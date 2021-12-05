package com.example.clip.repository;

import com.example.clip.enums.Status;
import com.example.clip.model.Payment;
import com.example.clip.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository<T extends Payment> extends PaymentRepository<T>{
    List<Transaction> findAllByUserIdNotNullAndStatus(Status status);
}
