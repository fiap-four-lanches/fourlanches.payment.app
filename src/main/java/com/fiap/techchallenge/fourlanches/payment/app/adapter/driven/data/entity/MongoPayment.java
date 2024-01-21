package com.fiap.techchallenge.fourlanches.payment.app.adapter.driven.data.entity;

import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.ExternalMetadata;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.OrderResume;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.Payment;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("payments")
public class MongoPayment {
    @Id
    private String id;
    private Long customerId;
    private OrderResume order;
    private PaymentStatus status;
    private Boolean isApproved;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String externalReference;
    private ExternalMetadata externalMetadata;

    public Payment toPayment() {
        return Payment.builder()
                .id(this.id)
                .customerId(this.customerId)
                .order(this.order)
                .status(this.status)
                .isApproved(this.isApproved)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .externalReference(this.externalReference)
                .externalMetadata(this.externalMetadata)
                .build();
    }

    public static MongoPayment fromPayment(Payment payment) {
        return MongoPayment.builder()
                .customerId(payment.getCustomerId())
                .order(payment.getOrder())
                .status(payment.getStatus())
                .isApproved(payment.getIsApproved())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .externalReference(payment.getExternalReference())
                .externalMetadata(payment.getExternalMetadata())
                .build();
    }
}