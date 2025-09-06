package dev.brianweloba.orderservice.enums;

public enum PaymentStatus {
    PENDING,     // Initial state, before any payment attempt
    AUTHORIZED,  // Payment authorized (funds held or captured)
    FAILED,      // Payment declined (card, balance, etc.)
    REFUNDED     // Payment rolled back after order cancellation
}
