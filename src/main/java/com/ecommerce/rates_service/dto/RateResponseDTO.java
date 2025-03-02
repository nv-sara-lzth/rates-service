package com.ecommerce.rates_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RateResponseDTO {

    private String result;
    private String description;
    private RateDTO rate;
    
}
