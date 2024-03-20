package com.fiap.techchallenge.fourlanches.payment.app.adapter.driver.api.consumer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.OrderResume;
import com.fiap.techchallenge.fourlanches.payment.app.domain.usecase.PaymentUseCase;
import com.fiap.techchallenge.fourlanches.payment.app.domain.valueobject.PaymentIntent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentQueueConsumerTest {
    private static final ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private PaymentQueueConsumer paymentQueueConsumer;

    @Mock
    private PaymentUseCase paymentUseCase;


    @Test
    void givenPaymentIntentMessageOkThenShouldUpdatePayment() throws JacksonException {
        // Arrange
        var paymentIntent = PaymentIntent.builder()
                .customerId(1L)
                .order(OrderResume.builder()
                        .id(2L)
                        .description("")
                        .totalPrice(BigDecimal.valueOf(21.50))
                        .build())
                .build();
        var paymentCreated = paymentIntent.toPayment();
        String requestId = "testRequestID";
        var json = mapper.writeValueAsString(paymentIntent);
        paymentIntent.setRequestId(requestId);
        when(paymentUseCase.createPaymentIntent(eq(paymentIntent))).thenReturn(paymentCreated);

        // Act
        paymentQueueConsumer.receivePaymentIntentMessage(json, requestId);

        // Assert
        // When using mockito.when will also validate that the function is being called
    }

    @Test
    void givenPaymentIntentMessageInvalidThenShouldThrowJacksonException() {
        // Arrange
        String invalidMessage = "{\"Amount\": 1000Currency\": \"USD\"}";
        String requestID = "testRequestID";

        // Assert
        assertThatExceptionOfType(JacksonException.class).isThrownBy(() -> {
            // Act
            paymentQueueConsumer.receivePaymentIntentMessage(invalidMessage, requestID);
        });
    }
}