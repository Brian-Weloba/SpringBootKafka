package dev.brianweloba.lib;

import java.time.Instant;
import java.util.UUID;

public record EventEnvelope<T> (
        String eventType,
        int eventVersion,
        Instant occuredAt,
        UUID traceId,
        T data
){}
