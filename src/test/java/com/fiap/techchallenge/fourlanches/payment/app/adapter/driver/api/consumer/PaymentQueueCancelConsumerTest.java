package com.fiap.techchallenge.fourlanches.payment.app.adapter.driver.api.consumer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.fourlanches.payment.app.application.dto.OrderStatusQueueMessageDTO;
import com.fiap.techchallenge.fourlanches.payment.app.domain.usecase.PaymentUseCase;
import com.fiap.techchallenge.fourlanches.payment.app.domain.valueobject.OrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentQueueCancelConsumerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Mock
    private PaymentUseCase paymentUseCase;

    @InjectMocks
    private PaymentQueueCancelConsumer paymentQueueCancelConsumer;

    @Test
    void givenCancellationMessageFromOrderWithStatusCanceledThenShouldCancelPayment() throws Exception {
        // Arrange
        String xRequestId = "12345";
        var orderId = 1L;
        var orderStatusQueueMessageDTO = OrderStatusQueueMessageDTO.builder()
                .status(OrderStatus.CANCELED)
                .orderId(orderId)
                .customerId(12345L)
                .origin("order")
                .build();
        String message = mapper.writeValueAsString(orderStatusQueueMessageDTO);

        // Act
        paymentQueueCancelConsumer.receivePaymentCancellationMessage(message, xRequestId);

        // Assert
        verify(paymentUseCase, times(1)).cancelPaymentByOrderId(eq(orderId));
    }

    @Test
    void givenCancellationMessageFromKitchenWithStatusCanceledThenShouldCancelPayment() throws Exception {
        // Arrange
        String xRequestId = "12345";
        var orderId = 1L;
        var orderStatusQueueMessageDTO = OrderStatusQueueMessageDTO.builder()
                .status(OrderStatus.CANCELED)
                .orderId(orderId)
                .customerId(12345L)
                .origin("kitchen")
                .build();
        String message = mapper.writeValueAsString(orderStatusQueueMessageDTO);

        // Act
        paymentQueueCancelConsumer.receivePaymentCancellationMessage(message, xRequestId);

        // Assert
        verify(paymentUseCase, times(1)).cancelPaymentByOrderId(eq(orderId));
    }

    @Test
    void givenCancellationMessageFromPaymentWithStatusCanceledThenShouldDoNothing() throws Exception {
        // Arrange
        String xRequestId = "12345";
        var orderId = 1L;
        var orderStatusQueueMessageDTO = OrderStatusQueueMessageDTO.builder()
                .status(OrderStatus.CANCELED)
                .orderId(orderId)
                .customerId(12345L)
                .origin("payment")
                .build();
        String message = mapper.writeValueAsString(orderStatusQueueMessageDTO);

        // Act
        paymentQueueCancelConsumer.receivePaymentCancellationMessage(message, xRequestId);

        // Assert
        verify(paymentUseCase, times(0)).cancelPaymentByOrderId(eq(orderId));
    }

    @Test
    void givenCancellationMessageFromOrderWithStatusDifferentFromCanceledThenShouldDoNothing() throws Exception {
        // Arrange
        String xRequestId = "12345";
        var orderId = 1L;
        var orderStatusQueueMessageDTO = OrderStatusQueueMessageDTO.builder()
                .status(OrderStatus.RECEIVED)
                .orderId(orderId)
                .customerId(12345L)
                .origin("order")
                .build();
        String message = mapper.writeValueAsString(orderStatusQueueMessageDTO);

        // Act
        paymentQueueCancelConsumer.receivePaymentCancellationMessage(message, xRequestId);

        // Assert
        verify(paymentUseCase, times(0)).cancelPaymentByOrderId(eq(orderId));
    }

    @Test
    void givenCancellationMessageMessageInvalidThenShouldThrowJacksonException() {
        // Arrange
        String invalidMessage = "{aa..bb}";
        String requestID = "testRequestID";

        // Assert
        assertThatExceptionOfType(JacksonException.class).isThrownBy(() -> {
            // Act
            paymentQueueCancelConsumer.receivePaymentCancellationMessage(invalidMessage, requestID);
        });
    }
}