package com.example.clip.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "user_id")
    private String userId;

}
