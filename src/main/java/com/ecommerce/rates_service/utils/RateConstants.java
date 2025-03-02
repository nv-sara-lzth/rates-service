package com.ecommerce.rates_service.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RateConstants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OperationResult{
        public static final String OK = "OK";
        public static final String KO = "KO";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OperationDescription{
        public static final String RATE_CREATED = "Tarifa con id {0} creada correctamente";
        public static final String RATE_UPDATED = "Precio de tarifa actualizado correctamente";
        public static final String RATE_DELETED = "Tarifa con id {0} eliminada correctamente";
        public static final String RATE_FOUND = "Tarifa encontrada";
        public static final String RATE_NOT_FOUND = "Tarifa no encontrada"; 

        public static final String RATE_GENERIC_ERROR = "Error inesperado {0} ";
    }
    
}
