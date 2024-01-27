package com.fiap.techchallenge.fourlanches.payment.app.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentGatewayTest {

    @Test
    void testEnumValues() {
        PaymentGateway mercadoPago = PaymentGateway.MERCADO_PAGO;

        assertEquals("mercado pago", mercadoPago.toString());
    }

    @Test
    void testEnumEquality() {
        PaymentGateway gateway1 = PaymentGateway.MERCADO_PAGO;
        PaymentGateway gateway2 = PaymentGateway.MERCADO_PAGO;

        assertEquals(gateway1, gateway2);
    }
}