package com.fiap.techchallenge.fourlanches.payment.app.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
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
    private String detail;
    private String externalOrderId;
    private InternalMetadata internalMetadata;

    @JsonIgnore
    public String getExternalOrderIdSafely() {
        return Objects.requireNonNullElse(this.externalOrderId, "");
    }

    @JsonIgnore
    public String getIdSafely() {
        return Objects.requireNonNullElse(this.id, "");
    }
}
