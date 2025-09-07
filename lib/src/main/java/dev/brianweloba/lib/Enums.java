package dev.brianweloba.lib;

public class Enums {
    public enum OrderStatus { PENDING, CONFIRMED, CANCELLED }

    public enum PaymentStatus { PENDING, AUTHORIZED, FAILED, REFUNDED }

    public enum InventoryStatus { PENDING, RESERVED, REJECTED, RELEASED }

    public enum OrderCancelReason {
        PAYMENT_FAILED, OUT_OF_STOCK, USER_REQUEST, TIMEOUT, SYSTEM_ERROR
    }

    public enum PaymentFailReason {
        CARD_DECLINED, INSUFFICIENT_FUNDS, RISK_BLOCK, GATEWAY_ERROR
    }

    public enum InventoryRejectReason {
        OUT_OF_STOCK, SKU_INVALID, WAREHOUSE_DOWN
    }
}
