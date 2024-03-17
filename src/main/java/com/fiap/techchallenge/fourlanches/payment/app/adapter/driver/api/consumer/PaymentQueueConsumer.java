package com.fiap.techchallenge.fourlanches.payment.app.adapter.driver.api.consumer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.fourlanches.payment.app.domain.usecase.PaymentUseCase;
import com.fiap.techchallenge.fourlanches.payment.app.domain.valueobject.PaymentIntent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.fiap.techchallenge.fourlanches.payment.app.application.constant.HeaderConstant.X_REQUEST_ID;

@Slf4j
@Component
@AllArgsConstructor
public class PaymentQueueConsumer {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private final PaymentUseCase paymentUseCase;

    @RabbitListener(queues = "${queue.payment.name}")
    public void receivePaymentIntentMessage(@Payload String message,
                                            @Header(X_REQUEST_ID) String xRequestId) throws JacksonException {
        log.info("received payment intent message [message:{}][request_id:{}]", message, xRequestId);
        var paymentIntent = mapper.readValue(message, PaymentIntent.class);
        paymentIntent.setRequestId(xRequestId);
        paymentUseCase.createPaymentIntent(paymentIntent);
        log.info("processed payment intent message [message:{}][request_id:{}]", message, xRequestId);
    }
}
