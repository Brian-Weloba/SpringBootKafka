package dev.brianweloba.lib;


import java.util.List;
import java.util.UUID;

public record InventoryRejectedData(
        UUID orderId,
        List<Item> items,
        Enums.InventoryRejectReason reason
) {
    public record Item(String sku, int requestedQty, int availableQty) {}
}