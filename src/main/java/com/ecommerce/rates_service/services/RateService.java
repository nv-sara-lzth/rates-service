package com.ecommerce.rates_service.services;

import java.time.LocalDate;
import java.util.Optional;

import com.ecommerce.rates_service.dto.RateDTO;

public interface RateService {

    RateDTO createRate(RateDTO rateDTO);

    Optional<RateDTO> findRateById(Long id);

    RateDTO updateRatePrice(Long id, Integer newPrice);

    void deleteRate(Long id);

    Optional<RateDTO> findApplicableRate(Integer brandId, Integer productId, LocalDate date);
}
