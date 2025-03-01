package com.ecommerce.rates_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.rates_service.model.Rate;

public interface RateRepository extends JpaRepository<Rate,Long>{
    
}
