package com.fiap.techchallenge.fourlanches.payment.app.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResume {
    private Long id;
    private BigDecimal totalPrice;
    private String description;
}
