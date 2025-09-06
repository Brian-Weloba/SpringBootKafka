package dev.brianweloba.paymentsservice.model;

import dev.brianweloba.paymentsservice.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    private UUID id;
    private UUID order_id;
    private Status status;
    private String comment;
    private UUID txn_ref;
    private Timestamp created_at;

    public Payment(){
        this.created_at = Timestamp.from(Instant.now());
    }

}
