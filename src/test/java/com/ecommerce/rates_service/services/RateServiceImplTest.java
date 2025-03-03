package com.ecommerce.rates_service.services;

import com.ecommerce.rates_service.dto.RateDTO;
import com.ecommerce.rates_service.dto.RateResponseDTO;
import com.ecommerce.rates_service.mappers.RateMapper;
import com.ecommerce.rates_service.model.Rate;
import com.ecommerce.rates_service.repositories.RateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static com.ecommerce.rates_service.utils.RateConstants.OperationDescription.*;
import static com.ecommerce.rates_service.utils.RateConstants.OperationResult.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateServiceImplTest {

    @Mock
    private RateRepository rateRepository;

    @Mock
    private RateMapper rateMapper;

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private RateServiceImpl rateService;

    private Rate rate;
    private RateDTO rateDTO;

    @BeforeEach
    void setUp() {
        rate = new Rate();
        rate.setId(1L);
        rate.setBrandId(1);
        rate.setProductId(1);
        rate.setStartDate(LocalDate.parse("2023-01-01"));
        rate.setEndDate(LocalDate.parse("2023-12-31"));
        rate.setPrice(1550);
        rate.setCurrencyCode("EUR");

        rateDTO = RateDTO.builder()
            .id(1L)
            .brandId(1)
            .productId(1)
            .startDate(LocalDate.parse("2023-01-01"))
            .endDate(LocalDate.parse("2023-12-31"))
            .price("1550")
            .currencyCode("EUR")
            .build();
    }

    @Test
    void createRate_success() {
        when(rateMapper.toEntity(rateDTO)).thenReturn(rate);
        when(rateRepository.save(rate)).thenReturn(rate);
        when(rateMapper.toResponseDto(null, OK, "Tarifa con id 1 creada correctamente"))
            .thenReturn(RateResponseDTO.builder().result(OK).description("Tarifa con id 1 creada correctamente").build());

        RateResponseDTO result = rateService.createRate(rateDTO);

        assertEquals(OK, result.getResult());
        assertEquals("Tarifa con id 1 creada correctamente", result.getDescription());
        verify(rateRepository, times(1)).save(rate);
    }

    @Test
    void findRateById_success() {
        when(rateRepository.findById(1L)).thenReturn(Optional.of(rate));
        when(rateMapper.toDto(rate)).thenReturn(rateDTO);
        when(currencyService.formatPrice(1550, "EUR")).thenReturn("15.50 €");
        when(rateMapper.toResponseDto(eq(rateDTO), eq(OK), eq(RATE_FOUND)))
            .thenReturn(RateResponseDTO.builder().result(OK).rate(rateDTO).description(RATE_FOUND).build());

        RateResponseDTO result = rateService.findRateById(1L);

        assertEquals(OK, result.getResult());
        assertEquals("15.50 €", result.getRate().getPrice());
        verify(currencyService, times(1)).formatPrice(1550, "EUR");
    }

    @Test
    void findRateById_notFound() {
        when(rateRepository.findById(1L)).thenReturn(Optional.empty());
        when(rateMapper.toResponseDto(null, KO, RATE_NOT_FOUND))
            .thenReturn(RateResponseDTO.builder().result(KO).description(RATE_NOT_FOUND).build());

        RateResponseDTO result = rateService.findRateById(1L);

        assertEquals(KO, result.getResult());
        assertEquals(RATE_NOT_FOUND, result.getDescription());
    }

    @Test
    void updateRatePrice_success() {
        when(rateRepository.findById(1L)).thenReturn(Optional.of(rate));
        when(rateRepository.save(rate)).thenReturn(rate);
        when(rateMapper.toResponseDto(null, OK, RATE_UPDATED))
            .thenReturn(RateResponseDTO.builder().result(OK).description(RATE_UPDATED).build());

        RateResponseDTO result = rateService.updateRatePrice(1L, 2000);

        assertEquals(OK, result.getResult());
        assertEquals(RATE_UPDATED, result.getDescription());
        verify(rateRepository, times(1)).save(rate);
    }

    @Test
    void findRateByMultipleFilter_success() {
        when(rateRepository.findByBrandIdAndProductIdAndDate(1, 1, LocalDate.parse("2023-06-15")))
            .thenReturn(Optional.of(rate));
        when(rateMapper.toDto(rate)).thenReturn(rateDTO);
        when(currencyService.formatPrice(1550, "EUR")).thenReturn("15.50 €");
        when(rateMapper.toResponseDto(eq(rateDTO), eq(OK), eq(RATE_FOUND)))
            .thenReturn(RateResponseDTO.builder().result(OK).rate(rateDTO).description(RATE_FOUND).build());

        RateResponseDTO result = rateService.findRateByMultipleFilter(1, 1, LocalDate.parse("2023-06-15"));

        assertEquals(OK, result.getResult());
        assertEquals("15.50 €", result.getRate().getPrice());
        verify(currencyService, times(1)).formatPrice(1550, "EUR");
    }

    @Test
    void deleteRate_success() {
        when(rateRepository.findById(1L)).thenReturn(Optional.of(rate));
        doNothing().when(rateRepository).deleteById(1L);
        when(rateMapper.toResponseDto(null, OK, "Tarifa con id 1 eliminada correctamente"))
            .thenReturn(RateResponseDTO.builder().result(OK).description("Tarifa con id 1 eliminada correctamente").build());

        RateResponseDTO result = rateService.deleteRate(1L);

        assertEquals(OK, result.getResult());
        assertEquals("Tarifa con id 1 eliminada correctamente", result.getDescription());
        verify(rateRepository, times(1)).deleteById(1L);
    }
}