package com.fiap.techchallenge.fourlanches.payment.app.application.usecase;

import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.Payment;
import com.fiap.techchallenge.fourlanches.payment.app.domain.repository.PaymentRepository;
import com.fiap.techchallenge.fourlanches.payment.app.domain.usecase.PaymentUseCase;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import utils.PaymentHelper;
import utils.PaymentIntentHelper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class PaymentUseCaseTest {

    private PaymentUseCase paymentUseCase;

    @Mock
    private PaymentRepository paymentRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {

        openMocks = MockitoAnnotations.openMocks(this);
        paymentUseCase = new PaymentUseCaseImpl(paymentRepository);
    }

    @AfterEach()
    void tearDown() throws Exception {
        openMocks.close();
    }

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
}
