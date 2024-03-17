package com.fiap.techchallenge.fourlanches.payment.app.adapter.driver.api.controller;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.fourlanches.payment.app.application.dto.OrderStatusQueueMessageDTO;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.Payment;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.PaymentStatus;
import com.fiap.techchallenge.fourlanches.payment.app.domain.usecase.PaymentUseCase;
import com.fiap.techchallenge.fourlanches.payment.app.domain.valueobject.OrderStatus;
import com.fiap.techchallenge.fourlanches.payment.app.domain.valueobject.PaymentIntent;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.fiap.techchallenge.fourlanches.payment.app.application.constant.HeaderConstant.X_REQUEST_ID;

@RestController
@RequestMapping("payments")
@AllArgsConstructor
public class PaymentController {

    private static String QUEUE_ORDER_STATUS_NAME;

    private final PaymentUseCase paymentUseCase;

    private final AmqpTemplate queueSender;

    @Value("${queue.order.status.name}")
    public void setQueueOrderStatusName(String value) {
        QUEUE_ORDER_STATUS_NAME = value;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Payment getPaymentById(@PathVariable String id) {
        return paymentUseCase.getPaymentById(id);
    }

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public Payment createPaymentIntent(@RequestBody @Valid PaymentIntent paymentIntent) {
        return paymentUseCase.createPaymentIntent(paymentIntent);
    }

    @PostMapping(value = "/callback", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> callback(@RequestParam String id,
                                         @RequestParam String topic,
                                         @RequestParam(required = false) Boolean payed) throws JacksonException {

        if (payed == null) {
            payed = Boolean.TRUE;
        }

        if (topic.equals("merchant_order ")) {
            var payment = paymentUseCase.findPaymentByExternalOrderId(id);
            // For now we will a third parameter just to mock, but when plugin into Mercado Pago it must call the
            // order endpoint https://api.mercadopago.com/merchant_orders/{id} to see if it was payed or not
            payment.setStatus(payed ? PaymentStatus.APPROVED : PaymentStatus.REJECTED);
            paymentUseCase.updatePayment(payment);

            MessageProperties messageProperties = new MessageProperties();
            Map<String, Object> headers = new HashMap<>();
            headers.put(X_REQUEST_ID, payment.getInternalMetadata().getOriginalRequestId());
            messageProperties.setHeaders(headers);
            var orderStatus = payed ? OrderStatus.RECEIVED : OrderStatus.CANCELED;
            var orderStatusMessage = OrderStatusQueueMessageDTO.fromPayment(payment, orderStatus);
            var objectMapper = new ObjectMapper();
            var paymentMessageJson = objectMapper.writeValueAsString(orderStatusMessage);
            var message = new Message(paymentMessageJson.getBytes(), messageProperties);

            queueSender.convertAndSend(QUEUE_ORDER_STATUS_NAME, message);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();
    }
}
