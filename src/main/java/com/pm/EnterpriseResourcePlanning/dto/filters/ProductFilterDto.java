package com.pm.EnterpriseResourcePlanning.dto.filters;

import com.pm.EnterpriseResourcePlanning.enums.ProductStatus;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductFilterDto(
        @Size(min = 5, max = 25)
        String name,
        @Positive
        Double priceGreater,
        @Positive
        Double priceLower,
        @Positive
        Integer unitGreater,
        @Positive
        Integer unitLower,
        ProductStatus status
) {
}
