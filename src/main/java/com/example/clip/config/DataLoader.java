package com.example.clip.config;

import com.example.clip.model.Payment;
import com.example.clip.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements ApplicationRunner {

    private PaymentRepository paymentRepository;

    @Autowired
    public DataLoader(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void run(ApplicationArguments args) {
        Payment payment = new Payment();
        payment.setAmount(new BigDecimal(123));
        payment.setUserId("user");
        paymentRepository.save(payment);
    }
}