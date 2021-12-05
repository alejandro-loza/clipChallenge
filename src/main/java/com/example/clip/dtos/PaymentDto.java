package com.example.clip.dtos;

import com.example.clip.model.Payment;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDto {

    private long id;

    private BigDecimal amount;

    private String userId;

    public PaymentDto(Payment payment) {
        this.id = payment.getId();
        this.amount = payment.getAmount();
        this.userId = payment.getUserId();
    }
}
