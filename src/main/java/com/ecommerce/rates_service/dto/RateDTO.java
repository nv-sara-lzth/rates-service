package com.ecommerce.rates_service.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RateDTO {
    
    private Long id;
    private Integer brandId;
    private Integer productId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String price;
    private String currencyCode;
    
}
