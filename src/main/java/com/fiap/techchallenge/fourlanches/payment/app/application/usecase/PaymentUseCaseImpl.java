package com.fiap.techchallenge.fourlanches.payment.app.application.usecase;

import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.Payment;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.PaymentStatus;
import com.fiap.techchallenge.fourlanches.payment.app.domain.repository.PaymentRepository;
import com.fiap.techchallenge.fourlanches.payment.app.domain.usecase.PaymentUseCase;
import com.fiap.techchallenge.fourlanches.payment.app.domain.valueobject.PaymentIntent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PaymentUseCaseImpl implements PaymentUseCase {

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
    public Payment updatePayment(Payment toUpdatePayment) {
        return repository.updatePayment(toUpdatePayment);
    }

    @Override
    public Payment cancelPaymentByOrderId(Long orderId) {
        var paymentToBeCanceled = repository.getPaymentByOrderId(orderId);
        // add here code to send request to refund when plug in into Mercado Pago gateway
        paymentToBeCanceled.setStatus(PaymentStatus.CANCELLED);
        paymentToBeCanceled.setDetail("order was cancelled");
        return repository.updatePayment(paymentToBeCanceled);
    }

    @Override
    public Payment findPaymentByExternalOrderId(String externalOrderId) {
        return repository.findPaymentByExternalOrderId(externalOrderId);
    }
}
