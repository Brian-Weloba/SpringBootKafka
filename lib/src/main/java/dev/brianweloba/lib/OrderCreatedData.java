package dev.brianweloba.lib;

import java.util.List;
import java.util.UUID;

public record OrderCreatedData(
        UUID orderId,
        UUID customerId,
        String currency,            // ISO 4217, e.g., "GBP"
        double total ,          // minor units
        List<OrderItem> items
) {}
