package com.example.clip.services;

import com.example.clip.dtos.TransactionDto;
import com.example.clip.model.Transaction;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> disbursementAll();
}
