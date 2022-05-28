package com.example.unitech.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String transactionId;

    private Date transactionDate;

    private BigDecimal amount;

    private String fromAccount;

    private String toAccount;

    private int status;


}
