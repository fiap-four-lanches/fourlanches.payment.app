package com.fiap.techchallenge.fourlanches.payment.app.domain.valueobject;

import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.ExternalMetadata;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.OrderResume;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.Payment;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.PaymentStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentIntent {

    @NotEmpty
    private Long customerId;

    @NotNull
    private OrderResume order;

    public Payment toPayment() {
        var now = LocalDateTime.now();
        return Payment.builder()
                .customerId(this.customerId)
                .order(this.order)
                .status(PaymentStatus.CREATED)
                .isApproved(false)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}