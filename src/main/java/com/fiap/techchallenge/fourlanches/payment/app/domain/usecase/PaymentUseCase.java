package com.fiap.techchallenge.fourlanches.payment.app.domain.usecase;

import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.Payment;
import com.fiap.techchallenge.fourlanches.payment.app.domain.valueobject.PaymentIntent;

public interface PaymentUseCase {

    Payment createPaymentIntent(PaymentIntent paymentIntent);

    Payment getPaymentById(String id);

    Payment updatePayment(Payment toUpdatePayment);

    Payment cancelPaymentByOrderId(Long orderId, String requestId);

    Payment findPaymentByExternalOrderId(String externalOrderId);
}
