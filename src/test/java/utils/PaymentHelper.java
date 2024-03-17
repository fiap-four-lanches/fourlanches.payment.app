package utils;

import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.OrderResume;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.Payment;
import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.PaymentStatus;

public abstract class PaymentHelper {

    public static Payment generatePayment(){
        var payment = PaymentIntentHelper.generatePaymentIntent().toPayment();
        payment.setId("Id");
        payment.setStatus(PaymentStatus.CREATED);
        payment.setOrder(OrderResume.builder().id(1L).build());
        return payment;
    }
}
