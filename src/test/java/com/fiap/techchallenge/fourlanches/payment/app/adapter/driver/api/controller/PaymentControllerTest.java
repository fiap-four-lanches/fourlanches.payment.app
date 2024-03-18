package com.fiap.techchallenge.fourlanches.payment.app.adapter.driver.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.InternalMetadata;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.OrderResume;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.Payment;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.PaymentStatus;
import com.fiap.techchallenge.fourlanches.payment.app.domain.usecase.PaymentUseCase;
import com.fiap.techchallenge.fourlanches.payment.app.domain.valueobject.PaymentIntent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import utils.PaymentHelper;
import utils.PaymentIntentHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ContextConfiguration(classes = TestConfiguration.class)
@ExtendWith(SpringExtension.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PaymentUseCase paymentUseCase;

    @Mock
    private AmqpTemplate queueSender;

    @InjectMocks
    private PaymentController controller;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
//                .setControllerAdvice(new OrderControllerAdvisor())
                .build();
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

    @Test
    void givenCallbackParametersPaymentIsNotPayedThenShouldCancelPayment() throws Exception {
        //Arrange
        var payment = Payment.builder()
                .id("1234567890")
                .status(PaymentStatus.CREATED)
                .order(OrderResume.builder().id(1L).build())
                .internalMetadata(InternalMetadata.builder().originalRequestId("req-ori-1").build())
                .build();

        when(paymentUseCase.findPaymentByExternalOrderId(Mockito.any()))
                .thenReturn(payment);

        // Act
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/payments/callback")
                        .param("id", "1234567890")
                        .param("topic", "merchant_order")
                        .param("payed", "false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Assert
        payment.setStatus(PaymentStatus.REJECTED);
        verify(paymentUseCase, times(1)).updatePayment(eq(payment));
        assertEquals(PaymentStatus.REJECTED.name(), payment.getStatus().name());
    }

    @Test
    void givenCallbackParametersPaymentIsPayedThenShouldApprovePayment() throws Exception {
        //Arrange
        var payment = Payment.builder()
                .id("1234567890")
                .status(PaymentStatus.CREATED)
                .order(OrderResume.builder().id(1L).build())
                .internalMetadata(InternalMetadata.builder().originalRequestId("req-ori-1").build())
                .build();

        when(paymentUseCase.findPaymentByExternalOrderId(Mockito.any()))
                .thenReturn(payment);

        // Act
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/payments/callback")
                        .param("id", "1234567890")
                        .param("topic", "merchant_order")
                        .param("payed", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Assert
        payment.setStatus(PaymentStatus.APPROVED);
        verify(paymentUseCase, times(1)).updatePayment(eq(payment));
        assertEquals(PaymentStatus.APPROVED.name(), payment.getStatus().name());
    }

    @Test
    void givenCallbackParametersPaymentPayedIstNotPresentThenShouldApprovePayment() throws Exception {
        //Arrange
        var payment = Payment.builder()
                .id("1234567890")
                .status(PaymentStatus.CREATED)
                .order(OrderResume.builder().id(1L).build())
                .internalMetadata(InternalMetadata.builder().originalRequestId("req-ori-1").build())
                .build();

        when(paymentUseCase.findPaymentByExternalOrderId(Mockito.any()))
                .thenReturn(payment);

        // Act
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/payments/callback")
                        .param("id", "1234567890")
                        .param("topic", "merchant_order")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Assert
        payment.setStatus(PaymentStatus.APPROVED);
        verify(paymentUseCase, times(1)).updatePayment(eq(payment));
        assertEquals(PaymentStatus.APPROVED.name(), payment.getStatus().name());
    }

    @Test
    void givenCallbackParametersPaymentIstNotMerchantOrderThenShouldDoNothing() throws Exception {

        // Arrange, Act & Assert
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/payments/callback")
                        .param("id", "1234567890")
                        .param("topic", "not_merchant_order")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    public static String AsJsonString(final Object object) throws JsonProcessingException {
        return new ObjectMapper().findAndRegisterModules().writeValueAsString(object);
    }
}
