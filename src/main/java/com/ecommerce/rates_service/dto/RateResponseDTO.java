package com.ecommerce.rates_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RateResponseDTO {

    private String result;
    private String description;
    private RateDTO rate;
    
}
