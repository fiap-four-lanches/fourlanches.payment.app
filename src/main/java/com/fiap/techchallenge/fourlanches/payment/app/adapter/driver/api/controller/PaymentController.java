package com.fiap.techchallenge.fourlanches.payment.app.adapter.driver.api.controller;

import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.Payment;
import com.fiap.techchallenge.fourlanches.payment.app.domain.usecase.PaymentUseCase;
import com.fiap.techchallenge.fourlanches.payment.app.domain.valueobject.PaymentIntent;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payments")
@AllArgsConstructor
public class PaymentController {

    private final PaymentUseCase paymentUseCase;

    @GetMapping(value = "/{id}", produces = "application/json")
    public Payment getPaymentById(@PathVariable String id) {
        return paymentUseCase.getPaymentById(id);
    }

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public Payment createPaymentIntent(@RequestBody @Valid PaymentIntent paymentIntent) {
        return paymentUseCase.createPaymentIntent(paymentIntent);
    }
}
