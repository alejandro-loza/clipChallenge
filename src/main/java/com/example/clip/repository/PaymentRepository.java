package com.example.clip.repository;

import com.example.clip.model.Payment;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository<T extends Payment> extends JpaRepository<T, Long> {
  List<Payment> findAllByUserIdNotNull();
}