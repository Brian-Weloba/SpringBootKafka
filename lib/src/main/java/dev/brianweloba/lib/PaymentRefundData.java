package dev.brianweloba.lib;


import java.util.UUID;

public record PaymentRefundData(
        UUID orderId,
        long amountMinor,
        String currency,
        String refundId,
        String reason
) {}