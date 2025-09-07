package dev.brianweloba.lib;

import java.util.UUID;

public record PaymentFailedData(
        UUID orderId,
        long amountMinor,
        String currency,
        Enums.PaymentFailReason reason
) {}