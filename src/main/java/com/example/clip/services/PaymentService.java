package com.example.clip.services;

import com.example.clip.dtos.PaymentDto;

import java.util.List;

public interface PaymentService {
    List<PaymentDto> findAll();
}
