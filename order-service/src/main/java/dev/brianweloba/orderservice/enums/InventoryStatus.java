package dev.brianweloba.orderservice.enums;

public enum InventoryStatus {
    PENDING,     // Initial state, before reservation attempt
    RESERVED,    // Stock successfully reserved
    REJECTED,    // Reservation failed (out of stock, invalid SKU)
    RELEASED     // Reservation was rolled back after cancellation
}
