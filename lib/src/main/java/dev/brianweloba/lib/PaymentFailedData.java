package dev.brianweloba.lib;

import java.util.UUID;

public record PaymentFailedData(
        UUID orderId,
        double amount,
        String currency,
        Enums.PaymentFailReason reason
) {}