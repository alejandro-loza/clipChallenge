package com.example.clip.model;

import com.example.clip.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction extends Payment {

    private static final double percent = 3.5;

    @Column(name = "disbursement")
    private BigDecimal disbursement;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    public void processDisbursement(){
        this.disbursement = this.getAmount().subtract(calculatePercentage());
        this.status = Status.PROCESSED;
    }

    private BigDecimal calculatePercentage() {
        return (this.getAmount().multiply(new BigDecimal(percent)))
                .divide(new BigDecimal(100),  2, RoundingMode.DOWN);
    }

}
