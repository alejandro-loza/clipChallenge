package com.example.clip.repository;


import com.example.clip.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface IReport {
    String getUserId();
    BigDecimal getPaymentsSum();
    List<Transaction> getTransactions();
}
