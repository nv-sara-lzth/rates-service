package com.ecommerce.rates_service.dto;

import lombok.Data;

@Data
public class CurrencyResponseDto {
    private String code;
    private String symbol;
    private int decimals;
}
