package dev.brianweloba.lib;


import java.util.UUID;

public record PaymentRefundData(
        UUID orderId,
        double amount,
        String currency,
        String refundId,
        String reason
) {}