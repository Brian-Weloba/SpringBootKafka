package dev.brianweloba.orderservice.service;

import dev.brianweloba.orderservice.models.Order;
import dev.brianweloba.orderservice.models.OrderItem;
import dev.brianweloba.orderservice.repository.OrderItemRepository;
import dev.brianweloba.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order addOrder(Order order) throws NullPointerException,IllegalArgumentException{
        Currency.getInstance(order.getCurrency());
        return this.orderRepository.save(order);
    }

    public Order findOrderById(Long id){
        Optional<Order> orderOptional = this.orderRepository.findById(id);
        return orderOptional.orElse(null);
    }

    public List<Order> findAllOrders(){
        return  this.orderRepository.findAll();
    }

    public Order addOrderItem(OrderItem orderItem, Long orderId) {
        Order order = findOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found with id: " + orderId);
        }

        order.addOrderItem(orderItem);
        return orderRepository.save(order);
    }
}
