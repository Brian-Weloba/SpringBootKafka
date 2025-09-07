package dev.brianweloba.lib;


import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EventEnvelope<T> {
    private String eventType;
    private int eventVersion;
    private Instant occuredAt;
    private UUID traceId;
    private T data;
}
