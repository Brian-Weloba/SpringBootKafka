package dev.brianweloba.paymentsservice.model;

import dev.brianweloba.lib.Enums;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID order_id;
    private Enums.PaymentStatus status;
    private String comment;
    private UUID txn_ref;
    private Timestamp created_at;
    private double amount;
    private String currency;

    public Payment(){
        this.created_at = Timestamp.from(Instant.now());
        this.txn_ref = UUID.randomUUID();
    }

}
