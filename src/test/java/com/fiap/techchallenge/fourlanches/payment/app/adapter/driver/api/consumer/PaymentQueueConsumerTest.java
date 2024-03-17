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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static com.fiap.techchallenge.fourlanches.payment.app.application.constant.HeaderConstant.X_REQUEST_ID;
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
        String requestId = "testRequestID";
        var json = mapper.writeValueAsString(paymentIntent);

        // Act
        paymentQueueConsumer.receivePaymentIntentMessage(json, requestId);

        // Assert
        paymentIntent.setRequestId(requestId);
        verify(paymentUseCase, times(1)).createPaymentIntent(paymentIntent);
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