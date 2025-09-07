package dev.brianweloba.lib;

public record OrderItem(
    String sku,
    int qty,
    double unitPrice
){}