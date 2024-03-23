package com.fiap.techchallenge.fourlanches.payment.app.adapter.driver.api.consumer;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class QueueConfiguration {

    public final static String X_REQUEST_ID = "X-Request-Id";

    private static String orderStatusQueue;

    private static String paymentQueue;

    private static String paymentCancelQueue;

    private static String exchangeChannel;

    @Value("${queue.order.status.name}")
    public void setOrderStatusQueue(String value) {
        orderStatusQueue = value;
    }

    @Value("${queue.payment.name}")
    public void setPaymentQueue(String value) {
        paymentQueue = value;
    }

    @Value("${queue.payment.cancel.name}")
    public void setPaymentCancelQueue(String value) {
        paymentCancelQueue = value;
    }

    @Value("${queue.exchange.name}")
    public void setExchangeChannel(String value) {
        exchangeChannel = value;
    }

    @Bean
    public Queue orderStatusQueue() {
        return QueueBuilder.durable(orderStatusQueue).build();
    }

    @Bean
    public Queue paymentQueue() {
        return QueueBuilder.durable(paymentQueue).build();
    }

    @Bean
    public Queue paymentCancelQueue() {
        return QueueBuilder.durable(paymentCancelQueue).build();
    }

    @Bean
    public Binding orderStatusBinding() {
        return BindingBuilder.bind(orderStatusQueue()).to(channelExchange()).with(orderStatusQueue);
    }

    @Bean
    public TopicExchange channelExchange() {
        return new TopicExchange(exchangeChannel, true, false);
    }
}
