package com.fiap.techchallenge.fourlanches.payment.app.domain.repository;

import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.Payment;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository {
    Payment create(Payment payment);

    Payment getPaymentById(String id);

    void updatePayment(Payment toUpdatePayment);
}
