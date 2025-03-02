package com.ecommerce.rates_service.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.rates_service.dto.RateDTO;
import com.ecommerce.rates_service.dto.RateResponseDTO;
import com.ecommerce.rates_service.mappers.RateMapper;
import com.ecommerce.rates_service.repositories.RateRepository;

import lombok.extern.slf4j.Slf4j;
import static java.text.MessageFormat.format;

@Service
@Slf4j
public class RateServiceImpl implements RateService {
    
    private final RateRepository rateRepository;
    private final RateMapper rateMapper;

    public RateServiceImpl(RateRepository rateRepository, RateMapper rateMapper){
        this.rateRepository = rateRepository;
        this.rateMapper = rateMapper;
    }
    
    @Override
    public RateResponseDTO createRate(RateDTO rateDTO) {

        log.info("Creando tarifa...");

        final var rate = rateMapper.toEntity(rateDTO);
        final var savedRate = rateRepository.save(rate);

        log.info(format("Tarifa creada con id [{0}]", savedRate.getId()));
        return null;
    }

    @Override
    public RateResponseDTO findRateById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findRateById'");
    }

    @Override
    public RateResponseDTO updateRatePrice(Long id, Integer newPrice) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRatePrice'");
    }

    @Override
    public RateResponseDTO deleteRate(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRate'");
    }

    @Override
    public RateResponseDTO findRateByBrandProductDate(Integer brandId, Integer productId, LocalDate date) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findRateByBrandProductDate'");
    }
    
}
