package com.ecommerce.rates_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.rates_service.dto.RateDTO;
import com.ecommerce.rates_service.dto.RateResponseDTO;
import com.ecommerce.rates_service.services.RateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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
    @Operation(summary = "Create a new rate", description = "Creates a new rate with the provided details")@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rate created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<RateResponseDTO> createRate(@RequestBody RateDTO rateDTO) {
        RateResponseDTO response = rateService.createRate(rateDTO);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("(/{id})")
    @Operation(summary = "Get rate by ID", description = "Retrieves a rate by its ID with formatted price")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rate found"),
        @ApiResponse(responseCode = "404", description = "Rate not found")
    })
    public ResponseEntity<RateResponseDTO> findRateById(@PathVariable Long id) {
        
        RateResponseDTO response = rateService.findRateById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/price")
    @Operation(summary = "Update rate price", description = "Updates the price of an existing rate")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Price updated successfully"),
        @ApiResponse(responseCode = "404", description = "Rate not found")
    })
    public ResponseEntity<RateResponseDTO> updateRatePrice(@PathVariable Long id, @RequestParam Integer newPrice) {
        RateResponseDTO response = rateService.updateRatePrice(id, newPrice);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete rate by ID", description = "Deletes a rate by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rate deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Rate not found")
    })
    public ResponseEntity<RateResponseDTO> deleteRate(@PathVariable Long id) {
        RateResponseDTO response = rateService.deleteRate(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    @Operation(summary = "Find rate by filters", description = "Finds a rate by brand ID, product ID, and date")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rate found"),
        @ApiResponse(responseCode = "404", description = "Rate not found")
    })
    public ResponseEntity<RateResponseDTO> findRateByMultipleFilter(
            @RequestParam Integer brandId,
            @RequestParam Integer productId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        RateResponseDTO response = rateService.findRateByMultipleFilter(brandId, productId, date);
        return ResponseEntity.ok(response);
    }    
}
