package com.fiap.techchallenge.fourlanches.payment.app.adapter.driver.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.fourlanches.payment.app.domain.usecase.PaymentUseCase;
import com.fiap.techchallenge.fourlanches.payment.app.domain.valueobject.PaymentIntent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import utils.PaymentHelper;
import utils.PaymentIntentHelper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PaymentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PaymentUseCase paymentUseCase;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){

        openMocks = MockitoAnnotations.openMocks(this);
        PaymentController paymentController = new PaymentController(paymentUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController)
                .build();
    }

    @AfterEach
    void tearDown() throws Exception{
        openMocks.close();

    }
    @Test
    void ShouldCreatePayment() throws Exception {
        // Arrange
        var payment = PaymentHelper.generatePayment();
        var paymentIntent = PaymentIntentHelper.generatePaymentIntent();

        when(paymentUseCase.createPaymentIntent(any(PaymentIntent.class)))
                .thenReturn(payment);

        // Act
        mockMvc.perform(
                    post("/payments")
                    .contentType("application/json")
                    .content(AsJsonString(paymentIntent)))
                .andExpect(status().isOk());


        // Assert
        verify(paymentUseCase, times(1)).createPaymentIntent(any(PaymentIntent.class));
    }

    @Test
    void ShouldGetPaymentById() throws Exception {
        //Arrange
        var payment = PaymentHelper.generatePayment();
        var id = payment.getId();

        when(paymentUseCase.getPaymentById(any(String.class)))
                .thenReturn(payment);
        // Act
        mockMvc.perform(get("/payments/{id}", id))
                .andExpect(status().isOk());

        // Assert
        verify(paymentUseCase, times(1)).getPaymentById(any(String.class));
    }

    public static String AsJsonString(final Object object) throws JsonProcessingException {
        var result = new ObjectMapper().findAndRegisterModules().writeValueAsString(object);
        return result;
    }
}
