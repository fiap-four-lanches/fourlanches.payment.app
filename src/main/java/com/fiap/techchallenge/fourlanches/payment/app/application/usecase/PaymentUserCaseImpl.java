package com.fiap.techchallenge.fourlanches.payment.app.application.usecase;

import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.Payment;
import com.fiap.techchallenge.fourlanches.payment.app.domain.repository.PaymentRepository;
import com.fiap.techchallenge.fourlanches.payment.app.domain.usecase.PaymentUseCase;
import com.fiap.techchallenge.fourlanches.payment.app.domain.valueobject.PaymentIntent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PaymentUserCaseImpl implements PaymentUseCase {

    private final PaymentRepository repository;

    @Override
    public Payment createPaymentIntent(PaymentIntent paymentIntent) {
        var payment = paymentIntent.toPayment();
        return repository.create(payment);
    }

    @Override
    public Payment getPaymentById(String id) {
        return repository.getPaymentById(id);
    }

    @Override
    public void updatePayment(Payment toUpdatePayment) {
        repository.updatePayment(toUpdatePayment);
    }
}
