package com.fiap.techchallenge.fourlanches.payment.app.application.usecase;

import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.InternalMetadata;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.Payment;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.PaymentStatus;
import com.fiap.techchallenge.fourlanches.payment.app.domain.repository.PaymentRepository;
import com.fiap.techchallenge.fourlanches.payment.app.domain.usecase.PaymentUseCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import utils.PaymentHelper;
import utils.PaymentIntentHelper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class PaymentUseCaseTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentUseCaseImpl paymentUseCase;


    @Test
    void ShouldCreatePayment() {
        // Arrange
        var paymentIntent = PaymentIntentHelper.generatePaymentIntent();
        var payment = PaymentHelper.generatePayment();

        when(paymentRepository.create(any(Payment.class))).thenReturn(payment);

        // Act
        var paymentResponse = paymentUseCase.createPaymentIntent(paymentIntent);

        // Assert
        assertThat(paymentResponse)
                .isNotNull()
                .isInstanceOf(Payment.class)
                .isEqualTo(payment);
        verify(paymentRepository, times((1))).create(any(Payment.class));

    }

    @Test
    void ShouldGetPaymentById() {
        // Arrange
        var payment = PaymentHelper.generatePayment();

        when(paymentUseCase.getPaymentById(any(String.class))).thenReturn(payment);

        // Act
        var paymentResponse = paymentUseCase.getPaymentById(payment.getId());

        // Assert
        assertThat(paymentResponse)
                .isNotNull()
                .isInstanceOf(Payment.class)
                .isEqualTo(payment);
        verify(paymentRepository, times((1))).getPaymentById(any(String.class));
    }

    @Test
    void ShouldReturnNullWhenPaymentNotFound() {
        // Arrange

        // Act
        var paymentResponse = paymentUseCase.getPaymentById("test");

        // Assert
        assertThat(paymentResponse)
                .isNull();

        verify(paymentRepository, times((1))).getPaymentById(any(String.class));
    }

    @Test
    void ShouldUpdatePayment() {
        // Arrange
        var payment = PaymentHelper.generatePayment();

        // Act
        paymentUseCase.updatePayment(payment);

        // Assert
        verify(paymentRepository, times((1))).updatePayment(any(Payment.class));
    }

    @Test
    void ShouldCancelPaymentByOrderId() {
        // Arrange
        var payment = PaymentHelper.generatePayment();
        var orderId = 1L;
        var requestId = "test-rq-1";

        when(paymentRepository.getPaymentByOrderId(eq(orderId))).thenReturn(payment);

        // Act
        paymentUseCase.cancelPaymentByOrderId(orderId, requestId);

        // Assert
        payment.setStatus(PaymentStatus.CANCELLED);
        payment.setInternalMetadata(InternalMetadata.builder().originalRequestId(requestId).build());
        verify(paymentRepository, times((1))).updatePayment(eq(payment));
    }

    @Test
    void ShouldFindPaymentByExternalOrderId() {
        // Arrange
        var payment = PaymentHelper.generatePayment();
        var externalOrderId = "external-1";

        when(paymentRepository.findPaymentByExternalOrderId(eq(externalOrderId))).thenReturn(payment);

        // Act
        var wantedPayment = paymentUseCase.findPaymentByExternalOrderId(externalOrderId);

        // Assert
        verify(paymentRepository, times((1))).findPaymentByExternalOrderId(eq(externalOrderId));
        assertThat(wantedPayment).isEqualTo(payment);
    }
}
