package com.ecommerce.rates_service.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.rates_service.dto.RateDTO;
import com.ecommerce.rates_service.dto.RateResponseDTO;
import com.ecommerce.rates_service.exceptions.RateNotFoundException;
import com.ecommerce.rates_service.mappers.RateMapper;
import com.ecommerce.rates_service.model.Rate;
import com.ecommerce.rates_service.repositories.RateRepository;
import com.ecommerce.rates_service.utils.RateConstants.OperationDescription;
import com.ecommerce.rates_service.utils.RateConstants.OperationResult;

import lombok.extern.slf4j.Slf4j;
import static java.text.MessageFormat.format;

@Service
@Slf4j
public class RateServiceImpl implements RateService {

    private final RateRepository rateRepository;
    private final RateMapper rateMapper;
    private final CurrencyService currencyService;

    public RateServiceImpl(RateRepository rateRepository, RateMapper rateMapper, CurrencyService currencyService) {
        this.rateRepository = rateRepository;
        this.rateMapper = rateMapper;
        this.currencyService = currencyService;
    }

    @Override
    public RateResponseDTO createRate(RateDTO rateDTO) {
        log.info("Creando tarifa...");
        Rate rate = rateMapper.toEntity(rateDTO);
        Rate savedRate = rateRepository.save(rate);
        log.info("Tarifa creada con id {}", savedRate.getId());
        return rateMapper.toResponseDto(null, OperationResult.OK, "Tarifa con id " + savedRate.getId() + " creada correctamente");
    }

    @Override
    public RateResponseDTO findRateById(Long id) {
        log.info(format("Buscando tarifa con id {}...", id));
        Rate rate = rateRepository.findById(id)
                .orElseThrow(() -> new RateNotFoundException("Rate with id " + id + " not found"));
        RateDTO rateDTO = rateMapper.toDto(rate);
        rateDTO.setPrice(currencyService.formatPrice(rate.getPrice(), rate.getCurrencyCode()));
        log.info(format("Tarifa encontrada con id {}", id));
        return rateMapper.toResponseDto(rateDTO, OperationResult.OK, OperationDescription.RATE_FOUND);
    }

    @Override
    public RateResponseDTO updateRatePrice(Long id, Integer newPrice) {
        log.info(format("Actualizando precio de tarifa con id {0}...", id));
        Rate rate = rateRepository.findById(id)
                .orElseThrow(() -> new RateNotFoundException("Rate with id " + id + " not found"));
        if (newPrice != null) {
            rate.setPrice(newPrice);
            rateRepository.save(rate);
            log.info(format("Precio actualizado para tarifa con id {0}", id));
        }
        return rateMapper.toResponseDto(null, OperationResult.OK, OperationDescription.RATE_UPDATED);
    }

    @Override
    public void deleteRate(Long id) {
        log.info("Eliminando tarifa con id {}...", id);
        rateRepository.findById(id)
                .orElseThrow(() -> new RateNotFoundException("Rate with id " + id + " not found"));
        rateRepository.deleteById(id);
        log.info(format("Eliminada tarifa con id {0}", id));
    }

    @Override
    public RateResponseDTO findRateByMultipleFilter(Integer brandId, Integer productId, LocalDate date) {
        log.info("Buscando tarifas con multiples filtros...");
        Rate rate = rateRepository.findByBrandIdAndProductIdAndDate(brandId, productId, date)
                .orElseThrow(() -> new RateNotFoundException(
                        "Rate not found for brandId " + brandId + ", productId " + productId + ", date " + date));
        RateDTO rateDTO = rateMapper.toDto(rate);
        rateDTO.setPrice(currencyService.formatPrice(rate.getPrice(), rate.getCurrencyCode()));
        log.info(format("Tarifa encontrada para los filtros id de marca [{0}] id de producto [{1}] y fecha [{2}]",
                brandId, productId, date));
        return rateMapper.toResponseDto(rateDTO, OperationResult.OK, OperationDescription.RATE_FOUND);
    }

}
