package com.ecommerce.rates_service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ecommerce.rates_service.dto.RateDTO;
import com.ecommerce.rates_service.model.Rate;

@Mapper(componentModel = "spring")
public interface RateMapper {

    @Mapping(target = "price", source = "price")
    RateDTO toDto(Rate rate);

    @Mapping(target = "price", source = "price")
    Rate toEntity(RateDTO rateDTO);
    
}
