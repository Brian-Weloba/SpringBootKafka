package dev.brianweloba.inventoryservice.model;

import dev.brianweloba.inventoryservice.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long order_id;
    private String sku;
    private int qty;
    private Status status;
    private Timestamp created_at;

    public  Reservation(){
        this.created_at = Timestamp.from(Instant.now());
    }
}
