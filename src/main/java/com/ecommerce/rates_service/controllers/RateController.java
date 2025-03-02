package com.ecommerce.rates_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.rates_service.dto.RateDTO;
import com.ecommerce.rates_service.dto.RateResponseDTO;
import com.ecommerce.rates_service.services.RateService;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/rates")
public class RateController {
    private final RateService rateService;

    public RateController(RateService rateService){
        this.rateService = rateService;
    }

    @PostMapping
    public ResponseEntity<RateResponseDTO> createRate(@RequestBody RateDTO rateDTO) {
        RateResponseDTO response = rateService.createRate(rateDTO);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("(/{id})")
    public ResponseEntity<RateResponseDTO> findRateById(@PathVariable Long id) {
        
        RateResponseDTO response = rateService.findRateById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/price")
    public ResponseEntity<RateResponseDTO> updateRatePrice(@PathVariable Long id, @RequestParam Integer newPrice) {
        RateResponseDTO response = rateService.updateRatePrice(id, newPrice);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RateResponseDTO> deleteRate(@PathVariable Long id) {
        RateResponseDTO response = rateService.deleteRate(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<RateResponseDTO> findRateByMultipleFilter(
            @RequestParam Integer brandId,
            @RequestParam Integer productId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        RateResponseDTO response = rateService.findRateByMultipleFilter(brandId, productId, date);
        return ResponseEntity.ok(response);
    }    
}
