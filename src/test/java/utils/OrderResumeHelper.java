package utils;

import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.OrderResume;

import java.math.BigDecimal;

public abstract class OrderResumeHelper {

    public static OrderResume generateOrderResume(){
        return OrderResume.builder()
                .id(1L)
                .totalPrice(BigDecimal.valueOf(31))
                .description("Some description")
                .build();
    }
}
