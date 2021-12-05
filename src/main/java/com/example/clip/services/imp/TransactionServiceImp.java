package com.example.clip.services.imp;

import com.example.clip.dtos.TransactionDto;
import com.example.clip.enums.Status;
import com.example.clip.model.Payment;
import com.example.clip.model.Transaction;
import com.example.clip.repository.TransactionRepository;
import com.example.clip.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImp implements TransactionService {

    @Autowired
    TransactionRepository<Transaction> transactionRepository;

    @Override
    public List<TransactionDto> disbursementAll() {
        List<Transaction> transactions = transactionRepository.findAllByUserIdNotNullAndStatus(Status.NEW);
        transactions.parallelStream()
                .forEach((Transaction t)->{
                    t.processDisbursement();
                    transactionRepository.save(t);
                });
        return transactions.stream()
                .map(TransactionDto::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

