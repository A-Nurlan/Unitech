package com.example.unitech.resource;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccountDetailsDto {

    private String pin;

    private String accountNumber;

    private boolean status;

    private BigDecimal balance;

    private Date createDate;
}
