package com.ecommerce.rates_service.services;

import java.time.LocalDate;

import com.ecommerce.rates_service.dto.RateDTO;
import com.ecommerce.rates_service.dto.RateResponseDTO;

public interface RateService {

    RateResponseDTO createRate(RateDTO rateDTO);

    RateResponseDTO findRateById(Long id);

    RateResponseDTO updateRatePrice(Long id, Integer newPrice);

    RateResponseDTO deleteRate(Long id);

    RateResponseDTO findRateByBrandProductDate(Integer brandId, Integer productId, LocalDate date);
}
