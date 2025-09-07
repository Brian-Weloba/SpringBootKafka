package dev.brianweloba.orderservice.controller;

import dev.brianweloba.orderservice.service.OrderService;
import dev.brianweloba.orderservice.models.Order;
import dev.brianweloba.orderservice.models.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public List<Order> getAll(){
        return this.orderService.findAllOrders();
    }

    @PostMapping("create/order")
    public ResponseEntity<Object> createOrder(@RequestBody Order order){
        ResponseEntity<Object> response;
        try {
            Order newOrder = this.orderService.addOrder(order);
            response = new ResponseEntity<>(newOrder, HttpStatus.CREATED);
        }catch (Exception e){
            if (e instanceof IllegalArgumentException || e instanceof  NullPointerException) {
                response = new ResponseEntity<>("Invalid or empty currency code provided.", HttpStatus.BAD_REQUEST);
            } else {
                response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return response;
    }

    @PostMapping("create/order-item/{orderId}")
    public ResponseEntity<Object> createOrderItem(@RequestBody OrderItem orderItem, @PathVariable String orderId){
        try {
            UUID id = UUID.fromString(orderId);
            Order order = this.orderService.addOrderItem(orderItem, id);
            if(order== null) return new ResponseEntity<>("Order not found",HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(order,HttpStatus.CREATED);
        }catch (NumberFormatException e){
            return new ResponseEntity<>("Invalid id",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{orderId}/submit-payment")
    public CompletableFuture<ResponseEntity<Map<String,String>>> submitPayment(@PathVariable UUID orderId) {
        return orderService.submitPaymentAsync(orderId)
                .thenApply(result -> {
                    if (result.success()) {
                        return ResponseEntity.accepted()
                                .body(Map.of("message", "Payment processing started",
                                        "status", "PROCESSING"));
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(Map.of("error", "Failed to process payment",
                                        "status", "FAILED"));
                    }
                })
                .exceptionally(ex -> {
                    log.error("Error processing payment for order {}", orderId, ex);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(Map.of("error", "Internal server error"));
                });
    }
}
