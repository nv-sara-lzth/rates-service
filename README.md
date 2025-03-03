# Rates Service

Rates Service es una API RESTful desarrollada con Spring Boot que permite gestionar tarifas (rates) para productos y marcas, almacenándolas en una base de datos PostgreSQL y formateando los precios usando un servicio externo simulado con WireMock.

## Características

- **Crear tarifas**: `POST /rates` - Almacena una nueva tarifa.
- **Obtener tarifa por ID**: `GET /rates/{id}` - Devuelve una tarifa con precio formateado.
- **Actualizar precio**: `PUT /rates/{id}/price` - Modifica el precio de una tarifa.
- **Buscar por filtros**: `GET /rates/filter` - Encuentra tarifas por marca, producto y fecha.
- **Eliminar tarifas**: `DELETE /rates/{id}` - Borra una tarifa existente.
- **Formateo de precios**: Integra un servicio externo (WireMock) para formatear precios según la moneda.

## Tecnologías

- **Spring Boot**: Framework principal para la API REST.
- **PostgreSQL**: Base de datos para almacenar tarifas.
- **WireMock**: Simulación del servicio externo de monedas.
- **Springdoc OpenAPI**: Generación de documentación OpenAPI/Swagger.
- **JUnit y Mockito**: Tests unitarios.

## Requisitos previos

- Java 17+
- Maven 3.8+
- PostgreSQL 14+
- WireMock Standalone 3.5.2

## Instalación

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/nv-sara-lzth/rates-service.git
   cd rates-service
