package utils;

import com.fiap.techchallenge.fourlanches.payment.app.domain.valueobject.PaymentIntent;

public abstract class PaymentIntentHelper {

    public static PaymentIntent generatePaymentIntent(){
        return PaymentIntent.builder()
                .customerId(2L)
                .order(OrderResumeHelper.generateOrderResume())
                .build();
    }
}
