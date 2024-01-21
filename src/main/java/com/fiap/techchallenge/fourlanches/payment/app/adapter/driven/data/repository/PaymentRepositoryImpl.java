package com.fiap.techchallenge.fourlanches.payment.app.adapter.driven.data.repository;

import com.fiap.techchallenge.fourlanches.payment.app.adapter.driven.data.MongoPaymentRepository;
import com.fiap.techchallenge.fourlanches.payment.app.adapter.driven.data.entity.MongoPayment;
import com.fiap.techchallenge.fourlanches.payment.app.application.execption.NotFound;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.Payment;
import com.fiap.techchallenge.fourlanches.payment.app.domain.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private MongoPaymentRepository mongoRepository;

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
    public void updatePayment(Payment toUpdatePayment) {
        mongoRepository.save(MongoPayment.fromPayment(toUpdatePayment));
    }
}
