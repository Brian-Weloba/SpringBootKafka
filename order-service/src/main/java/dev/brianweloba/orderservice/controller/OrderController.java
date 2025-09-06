package dev.brianweloba.orderservice.controller;

import dev.brianweloba.orderservice.service.OrderService;
import dev.brianweloba.orderservice.models.Order;
import dev.brianweloba.orderservice.models.OrderItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public Order createOrderItem(@RequestBody OrderItem orderItem, @PathVariable String orderId){
        UUID id = UUID.fromString(orderId);
        return this.orderService.addOrderItem(orderItem,id);
    }
}
