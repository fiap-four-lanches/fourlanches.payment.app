package com.fiap.techchallenge.fourlanches.payment.app.adapter.driven.data.entity;

import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.*;
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
    private String detail;
    private String externalOrderId;
    private InternalMetadata internalMetadata;

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
                .detail(this.detail)
                .externalOrderId(this.externalOrderId)
                .internalMetadata(this.internalMetadata)
                .build();
    }

    public static MongoPayment fromPayment(Payment payment) {
        var mongoPayment = MongoPayment.builder()
                .customerId(payment.getCustomerId())
                .order(payment.getOrder())
                .status(payment.getStatus())
                .isApproved(payment.getIsApproved())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .externalReference(payment.getExternalReference())
                .externalMetadata(payment.getExternalMetadata())
                .detail(payment.getDetail())
                .externalOrderId(payment.getExternalOrderId())
                .internalMetadata(payment.getInternalMetadata())
                .build();
        if (!payment.getIdSafely().isEmpty()) {
            mongoPayment.setId(payment.getIdSafely());
        }
        return mongoPayment;
    }
}