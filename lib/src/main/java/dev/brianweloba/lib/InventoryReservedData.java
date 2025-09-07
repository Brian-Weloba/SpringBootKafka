package dev.brianweloba.lib;

import java.util.List;
import java.util.UUID;

public record InventoryReservedData(
        UUID orderId,
        List<Item> items
) {
    public record Item(String sku, int qty) {}
}