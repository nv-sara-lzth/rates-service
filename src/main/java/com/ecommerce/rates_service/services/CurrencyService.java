package com.ecommerce.rates_service.services;

public interface CurrencyService {
    String formatPrice(Integer price, String currencyCode);
    
}
