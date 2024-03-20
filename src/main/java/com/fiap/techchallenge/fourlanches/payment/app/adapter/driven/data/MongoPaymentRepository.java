package com.fiap.techchallenge.fourlanches.payment.app.adapter.driven.data;

import com.fiap.techchallenge.fourlanches.payment.app.adapter.driven.data.entity.MongoPayment;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MongoPaymentRepository extends MongoRepository<MongoPayment, String> {
    long count();

    Optional<MongoPayment> findMongoPaymentByOrderId(Long orderId);

    Optional<MongoPayment> findMongoPaymentByExternalOrderId(String externalOrderId);
}