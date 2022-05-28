package com.example.unitech.service;

import java.math.BigDecimal;

public interface CurrencyService {
    BigDecimal getCurrency(String from, String to);
}
