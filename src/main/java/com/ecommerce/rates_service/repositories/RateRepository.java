package com.ecommerce.rates_service.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.rates_service.model.Rate;

public interface RateRepository extends JpaRepository<Rate,Long>{

    @Query("SELECT r FROM Rate r WHERE r.brandId = :brandId AND r.productId = :productId AND :date BETWEEN r.startDate AND r.endDate ORDER BY r.id DESC")
    Optional<Rate> findByBrandIdAndProductIdAndDate(@Param("brandId") Integer brandId, @Param("productId") Integer productId, @Param("date") LocalDate date);
    
}
