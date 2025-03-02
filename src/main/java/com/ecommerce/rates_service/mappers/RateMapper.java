package com.ecommerce.rates_service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.ecommerce.rates_service.dto.RateDTO;
import com.ecommerce.rates_service.dto.RateResponseDTO;
import com.ecommerce.rates_service.model.Rate;

@Mapper(componentModel = "spring")
public interface RateMapper {

    RateDTO toDto(Rate rate);

    @Mapping(target = "price", source = "price", qualifiedByName = "stringToInteger")
    Rate toEntity(RateDTO rateDTO);

    RateResponseDTO toResponseDto(RateDTO rate, String result, String description);

    @Named("stringToInteger")
    default Integer stringToInteger(String price){
        return price != null ? Integer.parseInt(price.replaceAll("[^0-9]", "")) : null;
    }
    
}
