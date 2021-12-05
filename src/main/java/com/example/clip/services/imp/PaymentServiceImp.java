package com.example.clip.services.imp;

import com.example.clip.dtos.PaymentDto;
import com.example.clip.model.Payment;
import com.example.clip.repository.PaymentRepository;
import com.example.clip.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImp implements PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public List<PaymentDto> findAll() {
        List<Payment> list = paymentRepository.findAllByUserIdNotNull();
        return list.stream()
                .map(PaymentDto::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
