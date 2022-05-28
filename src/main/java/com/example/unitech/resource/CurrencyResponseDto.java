package com.example.unitech.resource;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyResponseDto {
    private InfoDto info;

    @Data
    public static class InfoDto {
        private BigDecimal rate;
    }

}
