package com.example.unitech.controller;


import com.example.unitech.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public String getCurrency(@RequestParam("to") String to,
                                  @RequestParam("from") String from) {
        return currencyService.getCurrency(from.toUpperCase(), to.toUpperCase());
    }


}
