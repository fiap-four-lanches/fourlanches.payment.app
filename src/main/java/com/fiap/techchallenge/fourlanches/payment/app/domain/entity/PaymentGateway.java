package com.fiap.techchallenge.fourlanches.payment.app.domain.entity;

public enum PaymentGateway {
    MERCADO_PAGO("mercado pago");

    private final String value;

    PaymentGateway(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
