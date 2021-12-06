package com.example.clip.services;

import com.example.clip.dtos.ReportDto;
import com.example.clip.dtos.TransactionDto;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> disbursementAll();
    List<ReportDto> report();
}
