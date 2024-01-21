package com.fiap.techchallenge.fourlanches.payment.app.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class Payment {
    private String id;
    private Long customerId;
    private OrderResume order;
    private PaymentStatus status;
    private Boolean isApproved;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String externalReference;
    private ExternalMetadata externalMetadata;
}
