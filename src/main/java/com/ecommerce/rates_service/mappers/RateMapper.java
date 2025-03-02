package com.ecommerce.rates_service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ecommerce.rates_service.dto.RateDTO;
import com.ecommerce.rates_service.dto.RateResponseDTO;
import com.ecommerce.rates_service.model.Rate;

@Mapper(componentModel = "spring")
public interface RateMapper {

    @Mapping(target = "price", source = "price")
    RateDTO toDto(Rate rate);

    @Mapping(target = "price", source = "price")
    Rate toEntity(RateDTO rateDTO);

    default RateResponseDTO toResponseDto(Rate rate, String result, String description) {
    return RateResponseDTO.builder()
        .result(result)
        .rate(rate != null ? toDto(rate) : null)
        .description(description)
        .build();
}
    
}
