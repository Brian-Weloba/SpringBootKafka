package dev.brianweloba.lib;

import java.util.UUID;

public record OrderCancelledData(
        UUID orderId,
        Enums.OrderCancelReason reason
) {}
