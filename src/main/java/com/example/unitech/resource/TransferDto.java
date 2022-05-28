package com.example.unitech.resource;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransferDto {

    private String transactionId;

    private Date transactionDate;

    private BigDecimal amount;

    private String fromAccount;

    private String toAccount;
}
