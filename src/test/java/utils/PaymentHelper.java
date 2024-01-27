package utils;

import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.Payment;

public abstract class PaymentHelper {

    public static Payment generatePayment(){
        var payment = PaymentIntentHelper.generatePaymentIntent().toPayment();
        payment.setId("Id");
        return payment;
    }
}
