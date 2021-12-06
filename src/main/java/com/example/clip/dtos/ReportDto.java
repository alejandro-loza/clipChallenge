package com.example.clip.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReportDto {
    String userId;
    BigDecimal paymentsSum;
    BigDecimal newPaymentsDisbursement;
    BigDecimal newPaymentsAmount;
}
