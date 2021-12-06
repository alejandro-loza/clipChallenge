package com.example.clip.services.imp;

import com.example.clip.dtos.ReportDto;
import com.example.clip.dtos.TransactionDto;
import com.example.clip.enums.Status;
import com.example.clip.model.Payment;
import com.example.clip.model.Transaction;
import com.example.clip.repository.TransactionRepository;
import com.example.clip.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    @Override
    public List<ReportDto> report() {
        List<ReportDto> reportDtos = new ArrayList<>();
        List<Transaction> transactions = transactionRepository.findAllByAmountNotNull();

        var userGroup = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getUserId));

        userGroup.forEach((k,v) -> {

            var paymentsSum = v.stream()
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            var disbursementSum = v.stream()
                    .filter(transaction -> transaction.getStatus() == Status.PROCESSED)
                    .map(Transaction::getDisbursement)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            var newAmountSum = v.stream()
                    .filter(transaction -> transaction.getStatus() == Status.NEW)
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            ReportDto reportDto = new ReportDto();
            reportDto.setUserId(k);
            reportDto.setPaymentsSum(paymentsSum);
            reportDto.setNewPaymentsDisbursement(disbursementSum);
            reportDto.setNewPaymentsAmount(newAmountSum);
            reportDtos.add(reportDto);
        });


        return reportDtos;
    }

    private TransactionDto convertToDto(Transaction transaction){
        return new TransactionDto(transaction);
    }

    private Transaction disbursement(Transaction transactions) {
        transactions.processDisbursement();
        return transactionRepository.save(transactions);
    }
}

