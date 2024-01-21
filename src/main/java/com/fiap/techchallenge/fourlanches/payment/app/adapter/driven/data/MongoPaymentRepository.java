package com.fiap.techchallenge.fourlanches.payment.app.adapter.driven.data;

import com.fiap.techchallenge.fourlanches.payment.app.adapter.driven.data.entity.MongoPayment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoPaymentRepository extends MongoRepository<MongoPayment, String> {
    long count();
}