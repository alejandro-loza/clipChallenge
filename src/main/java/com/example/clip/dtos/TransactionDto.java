package com.example.clip.dtos;

import com.example.clip.enums.Status;
import com.example.clip.model.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionDto extends PaymentDto{
    private BigDecimal disbursement;
    private Status status;

    public TransactionDto(Transaction transaction) {
        super();
        this.setId(transaction.getId());
        this.setAmount(transaction.getAmount());
        this.setUserId(transaction.getUserId());
        this.disbursement = transaction.getDisbursement();
        this.status = transaction.getStatus();
    }

}
