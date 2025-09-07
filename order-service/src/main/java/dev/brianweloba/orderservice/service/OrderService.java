package dev.brianweloba.orderservice.service;

import dev.brianweloba.lib.EventEnvelope;
import dev.brianweloba.lib.OrderCreatedData;
import dev.brianweloba.orderservice.enums.PaymentStatus;
import dev.brianweloba.orderservice.kafka.MessageProducer;
import dev.brianweloba.orderservice.models.Order;
import dev.brianweloba.orderservice.models.OrderItem;
import dev.brianweloba.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MessageProducer messageProducer;

    public OrderService(OrderRepository orderRepository, MessageProducer messageProducer) {
        this.orderRepository = orderRepository;
        this.messageProducer = messageProducer;
    }

    private static OrderCreatedData getOrderCreatedData(Order order) {
        List<dev.brianweloba.lib.OrderItem> items = new ArrayList<>();
        for (OrderItem item : order.getOrderItems()) {
            items.add(new dev.brianweloba.lib.OrderItem(item.getSku(), item.getQty(), item.getUnitPrice()));
        }

        return new OrderCreatedData(
                order.getId(),
                order.getCustomerId(),
                order.getCurrency(),
                order.getAmount(),
                items
        );
    }

    public Order addOrder(Order order) throws NullPointerException, IllegalArgumentException {
        Currency.getInstance(order.getCurrency());
        return this.orderRepository.save(order);
    }

    public Order findOrderById(UUID id) {
        Optional<Order> orderOptional = this.orderRepository.findById(id);
        return orderOptional.orElse(null);
    }

    public List<Order> findAllOrders() {
        return this.orderRepository.findAll();
    }

    public Order addOrderItem(OrderItem orderItem, UUID orderId) {
        Order order = findOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found with id: " + orderId);
        }

        order.addOrderItem(orderItem);
        return orderRepository.save(order);

    }

    @Transactional
    public CompletableFuture<PaymentResult> submitPaymentAsync(UUID orderId) {
        Order order = findOrderById(orderId);

        EventEnvelope<OrderCreatedData> data = createOrderEvent(order);
        CompletableFuture<SendResult<String, EventEnvelope<?>>> future =
                this.messageProducer.createOrder(data);

        return future
                .thenApply(result -> {
                    log.info("Order event sent successfully: {}", result.getRecordMetadata());
                    order.setPayment_status(PaymentStatus.PROCESSING);
                    orderRepository.save(order);
                    return new PaymentResult(true, "Payment processing started");
                })
                .exceptionally(ex -> {
                    log.error("Failed to send order event", ex);
                    order.setPayment_status(PaymentStatus.FAILED);
                    orderRepository.save(order);
                    return new PaymentResult(false, "Failed to process payment: " + ex.getMessage());
                });
    }

    private EventEnvelope<OrderCreatedData> createOrderEvent(Order order) {
        return new EventEnvelope<>(
                "order.created",
                1,
                Instant.now(),
                UUID.randomUUID(),
                getOrderCreatedData(order)
        );
    }

    public record PaymentResult(boolean success, String message) {

    }
}
