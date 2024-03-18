package com.fiap.techchallenge.fourlanches.payment.app.adapter.driven.data.repository;

import com.fiap.techchallenge.fourlanches.payment.app.adapter.driven.data.MongoPaymentRepository;
import com.fiap.techchallenge.fourlanches.payment.app.adapter.driven.data.entity.MongoPayment;
import com.fiap.techchallenge.fourlanches.payment.app.application.execption.NotFound;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.Payment;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.PaymentStatus;
import com.fiap.techchallenge.fourlanches.payment.app.domain.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final MongoPaymentRepository mongoRepository;

    @Autowired
    public PaymentRepositoryImpl(MongoPaymentRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public Payment create(Payment payment) {
        var mongoPayment = MongoPayment.fromPayment(payment);
        mongoRepository.insert(mongoPayment);
        return mongoPayment.toPayment();
    }

    @Override
    public Payment getPaymentById(String id) {
        var found = mongoRepository.findById(id);

        if (found.isEmpty()) {
            throw new NotFound("payment not found");
        }

        return found.get().toPayment();
    }

    @Override
    public Payment updatePayment(Payment toUpdatePayment) {
        mongoRepository.save(MongoPayment.fromPayment(toUpdatePayment));
        return toUpdatePayment;
    }

    @Override
    public Payment getPaymentByOrderId(Long orderId) {
        var found = mongoRepository.findMongoPaymentByOrderId(orderId);

        if (found.isEmpty()) {
            throw new NotFound("payment not found");
        }

        return found.get().toPayment();
    }

    @Override
    public Payment findPaymentByExternalOrderId(String externalOrderId) {
        var found = mongoRepository.findMongoPaymentByExternalOrderId(externalOrderId);

        if (found.isEmpty()) {
            throw new NotFound("payment not found");
        }

        return found.get().toPayment();
    }
}
