package com.fiap.techchallenge.fourlanches.payment.app.adapter.driver.api.consumer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.fourlanches.payment.app.application.dto.OrderStatusQueueMessageDTO;
import com.fiap.techchallenge.fourlanches.payment.app.domain.usecase.PaymentUseCase;
import com.fiap.techchallenge.fourlanches.payment.app.domain.valueobject.PaymentIntent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.fiap.techchallenge.fourlanches.payment.app.application.constant.HeaderConstant.X_REQUEST_ID;

@Slf4j
@Component
@AllArgsConstructor
public class PaymentQueueCancelConsumer {

    @Autowired
    private final PaymentUseCase paymentUseCase;
    private static final ObjectMapper mapper = new ObjectMapper();

    @RabbitListener(queues = "${queue.payment.cancel.name}")
    public void receivePaymentCancellationMessage(@Payload String message,
                                                  @Header(X_REQUEST_ID) String xRequestId) throws JacksonException {

        var paymentCancellationMessage = mapper.readValue(message, OrderStatusQueueMessageDTO.class);
        paymentUseCase.cancelPaymentByOrderId(paymentCancellationMessage.getOrderId());
    }
}
