package com.ecommerce.rates_service.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.rates_service.dto.CurrencyResponseDto;
import com.ecommerce.rates_service.utils.RateConstants.CurrencyServiceValorDefecto;
import com.ecommerce.rates_service.utils.RateConstants.OperationDescription;

import static java.text.MessageFormat.format;

@Service
public class CurrencyServiceImpl implements CurrencyService{

    private final RestTemplate restTemplate;
    private final String currencyServiceUrl;
    private final String username;
    private final String password;

    public CurrencyServiceImpl(RestTemplate restTemplate,
        @Value("${currency.service.url}") String currencyServiceUrl,
        @Value("${currency.service.username}") String username,
        @Value("${currency.service.password}") String password){
            this.restTemplate = restTemplate;
            this.currencyServiceUrl = currencyServiceUrl;
            this.username = username;
            this.password = password;
        }

    @Override
    public String formatPrice(Integer price, String currencyCode) {
        String url = format("{0}{1}", currencyServiceUrl, format(CurrencyServiceValorDefecto.GET_CURRENCY_BY_CODE, currencyCode));
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        CurrencyResponseDto response = restTemplate.exchange(url, HttpMethod.GET, entity, CurrencyResponseDto.class).getBody();
        if (response != null && price != null) {
            double formattedPrice = price / Math.pow(10, response.getDecimals());
            String pattern = "{0,number,#." + "0".repeat(response.getDecimals()) + "}{1}";
            return format(pattern, formattedPrice, response.getSymbol());
        }

        return OperationDescription.NONE;
    }

}
