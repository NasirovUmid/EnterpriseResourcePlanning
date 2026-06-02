package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record ProductSalesRequestDto(
        @NotNull
        UUID productId,
        @NotNull
        UUID salesId,
        @Positive
        Integer quantity
) {
}
