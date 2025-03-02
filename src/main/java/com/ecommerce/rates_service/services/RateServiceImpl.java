package com.ecommerce.rates_service.services;

import java.time.LocalDate;
import org.springframework.stereotype.Service;

import com.ecommerce.rates_service.dto.RateDTO;
import com.ecommerce.rates_service.dto.RateResponseDTO;
import com.ecommerce.rates_service.mappers.RateMapper;
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

        try {
            final var rate = rateMapper.toEntity(rateDTO);
            final var savedRate = rateRepository.save(rate);
            
            log.info(format("Tarifa creada con id [{0}]", savedRate.getId()));

            return rateMapper.toResponseDto(null, OperationResult.OK, format(OperationDescription.RATE_CREATED, savedRate.getId()));
        } catch (Exception e) {
            log.error(format("Error inesperado creando la tarifa {0}", e.getMessage()));
            return rateMapper.toResponseDto(null, OperationResult.KO, format(OperationDescription.RATE_GENERIC_ERROR, " creando tarifa"));
        }
    }

    @Override
    public RateResponseDTO findRateById(Long id) {
        log.info(format("Buscando tarifa con id {0}...", id));
        try {
            final var rateOptional = rateRepository.findById(id);
            if (rateOptional.isPresent()) {
                final var rate = rateOptional.get();
                RateDTO rateDTO = rateMapper.toDto(rate);
                rateDTO.setPrice(currencyService.formatPrice(rate.getPrice(), rate.getCurrencyCode()));
                log.info(format("Tarifa encontrada con id {0}", id));
                return rateMapper.toResponseDto(rateDTO, OperationResult.OK, OperationDescription.RATE_FOUND);
            }else {
                log.warn(format("No se encontro ninguna tarifa con id {0}", id));
                return rateMapper.toResponseDto(null, OperationResult.KO, OperationDescription.RATE_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(format("Error inesperado buscando tarifa con id [{0}] Causa [{1}]", id, e.getMessage()));
            return rateMapper.toResponseDto(null, OperationResult.KO, format(OperationDescription.RATE_GENERIC_ERROR, "buscando tarifa"));
        }
    }

    @Override
    public RateResponseDTO updateRatePrice(Long id, Integer newPrice) {
        log.info(format("Actualizando precio de tarifa con id {0}...", id));
        try {
            if (newPrice != null && newPrice < 0) {
                log.warn(format("Intento de actualizacion de precio de valor negativo en tarifa con id {0}", id));
                return rateMapper.toResponseDto(null, OperationResult.KO, OperationDescription.RATE_NEGATIVE_PRICE);
                
            }

            final var rateOptional = rateRepository.findById(id);
            if (rateOptional.isPresent()) {
                final var rate = rateOptional.get();
                rate.setPrice(newPrice);
                rateRepository.save(rate);

                log.info(format("Precio actualizado para tarifa con id {0}", id));
                return rateMapper.toResponseDto(null, OperationResult.OK, OperationDescription.RATE_UPDATED);  
            }else{
                log.warn(format("No se encontro la tarifa con id {0}", id));
                return rateMapper.toResponseDto(null, OperationResult.KO, OperationDescription.RATE_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(format("Error inesperado actualizando tarifa con id [{0}] Causa [{1}]", id, e.getMessage()));
            return rateMapper.toResponseDto(null, OperationResult.KO, format(OperationDescription.RATE_GENERIC_ERROR, "actualizando tarifa"));
        }
    }

    @Override
    public RateResponseDTO deleteRate(Long id) {
        log.info(format("Eliminando tarifa con id {0}...", id));
        try {
            final var rateOptional = rateRepository.findById(id);
            if (rateOptional.isPresent()) {
                rateRepository.deleteById(id);
                log.info(format("Eliminada tarifa con id {0}", id));
                return rateMapper.toResponseDto(null, OperationResult.OK, format(OperationDescription.RATE_DELETED,id));                
            }else {
                log.warn(format("No se encontro tarifa con id {0}", id));
                return rateMapper.toResponseDto(null, OperationResult.KO, OperationDescription.RATE_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(format("Error inesperado eliminando tarifa con id [{0}] Cause [{1}]", id, e.getMessage()));
            return rateMapper.toResponseDto(null, OperationResult.KO, format(OperationDescription.RATE_GENERIC_ERROR, "eliminando tarifa"));
        }
    }

    @Override
    public RateResponseDTO findRateByMultipleFilter(Integer brandId, Integer productId, LocalDate date) {
        log.info("Buscando tarifas con multiples filtros...");

        try {
            final var rateOptional = rateRepository.findByBrandIdAndProductIdAndDate(brandId, productId, date);
            if (rateOptional.isPresent()) {
                final var rate = rateOptional.get();

                RateDTO rateDTO = rateMapper.toDto(rate);
                rateDTO.setPrice(currencyService.formatPrice(rate.getPrice(), rate.getCurrencyCode()));

                log.info(format("Tarifa encontrada para los filtros id de marca [{0}] id de producto [{1}] y fecha [{2}]", brandId, productId, date));
                return rateMapper.toResponseDto(rateDTO, OperationResult.OK, OperationDescription.RATE_FOUND);
            }else {
                log.warn(format("No se encontro ninguna tarifa para id de marca [{0}] id de producto [{1}] y fecha [{2}]", brandId, productId, date));
                return rateMapper.toResponseDto(null, OperationResult.KO, format("{0} para los filtros especificados", OperationDescription.RATE_NOT_FOUND));
            }
        } catch (Exception e) {
            log.error(format("Error inesperado buscando tarifa con id de marca [{0}] id de producto [{1}] y fecha [{2}] Causa [{3}]", brandId, productId, date, e.getMessage()));
            return rateMapper.toResponseDto(null, OperationResult.KO, format(OperationDescription.RATE_GENERIC_ERROR, "buscando tarifa con filtro multiple"));
        }
    }

}
