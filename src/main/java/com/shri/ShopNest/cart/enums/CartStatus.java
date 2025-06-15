package com.shri.ShopNest.cart.enums;

public enum CartStatus {
    ACTIVE, ABANDONED, PAYMENT_FAILED, COMPLETED;

    public boolean isActive() {
        return this == ACTIVE;
    }
}
