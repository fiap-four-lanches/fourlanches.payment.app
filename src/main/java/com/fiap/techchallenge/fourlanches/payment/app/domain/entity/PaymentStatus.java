package com.fiap.techchallenge.fourlanches.payment.app.domain.entity;

public enum PaymentStatus {
    CREATED("created"),
    AWAITING("awaiting"),
    CANCELLED("cancelled"),
    REJECTED("rejected"),
    APPROVED("approved");

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
