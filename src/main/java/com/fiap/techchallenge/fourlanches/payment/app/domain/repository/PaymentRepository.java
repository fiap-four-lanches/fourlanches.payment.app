package com.fiap.techchallenge.fourlanches.payment.app.domain.repository;

import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.Payment;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository {
    Payment create(Payment payment);

    Payment getPaymentById(String id);

    Payment updatePayment(Payment toUpdatePayment);

    Payment getPaymentByOrderId(Long orderId);

    Payment findPaymentByExternalOrderId(String externalOrderId);
}
