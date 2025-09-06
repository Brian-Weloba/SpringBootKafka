package dev.brianweloba.orderservice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.brianweloba.orderservice.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Setter
@Getter
@Entity
@Data
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Status status;
    private double amount = 0;
    //TODO: Add Currency handling
    private String currency;

    private PaymentStatus payment_status;
    private InventoryStatus inventory_status;
    @Version
    private int version;
    private Timestamp created_at;
    private Timestamp updated_at;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {
        this.status = Status.PENDING;
        this.payment_status = PaymentStatus.PENDING;
        this.inventory_status = InventoryStatus.PENDING;
        this.created_at = Timestamp.from(Instant.now());
        this.updated_at = Timestamp.from(Instant.now());
    }

    public void calculateAmount(){
        double total = 0;
        if(orderItems!=null && !orderItems.isEmpty()) {
            for(OrderItem item : orderItems){
                total += item.getUnitPrice()* item.getQty();
            }
        }
        this.amount = total;
    }

    public void addOrderItem(OrderItem item){
        item.setOrder(this);
        this.orderItems.add(item);
        calculateAmount();
    }

    public void removeOrderItem(OrderItem item){
        this.orderItems.remove(item);
        calculateAmount();
    }

    @PreUpdate
    public void onUpdate(){
        this.updated_at = Timestamp.from(Instant.now());
    }
}
