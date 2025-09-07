package dev.brianweloba.lib;

import java.util.UUID;

public record PaymentAuthorizedData(
        UUID orderId,
        double amount,
        String currency,
        String authId,          // gateway auth reference
        String provider         // optional: "stripe", "adyen", etc.
) {}
