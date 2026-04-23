package com.pm.EnterpriseResourcePlanning.dto.responsdtos;

import com.pm.EnterpriseResourcePlanning.enums.ProductStatus;

import java.util.UUID;

public record ProductResponseDto(
        UUID id,
        String name,
        Double price,
        ProductStatus status
) {
}
