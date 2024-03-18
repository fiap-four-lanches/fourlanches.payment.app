package com.fiap.techchallenge.fourlanches.payment.app.application.dto;

import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.Payment;
import com.fiap.techchallenge.fourlanches.payment.app.domain.valueobject.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusQueueMessageDTO implements Serializable {
    private Long orderId;
    private Long customerId;
    private OrderStatus status;
    private String origin;

    public static OrderStatusQueueMessageDTO fromPayment(Payment payment, OrderStatus orderStatus) {
        return OrderStatusQueueMessageDTO.builder()
                .orderId(payment.getOrder().getId())
                .customerId(payment.getCustomerId())
                .status(orderStatus)
                .origin("payment")
                .build();
    }
}
