package dev.brianweloba.lib;

import java.util.List;
import java.util.UUID;

public record InventoryReleasedData(
        UUID orderId,
        List<Item> items,
        String reason
) {
    public record Item(String sku, int qty) {}
}
