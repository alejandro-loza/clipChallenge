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
        return  transactionRepository
                .findAllByUserIdNotNullAndStatus(Status.NEW).parallelStream()
                    .map(this::disbursement)
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
    }

    private TransactionDto convertToDto(Transaction transaction){
        return new TransactionDto(transaction);
    }

    private Transaction disbursement(Transaction transactions) {
        transactions.processDisbursement();
        return transactionRepository.save(transactions);
    }
}

