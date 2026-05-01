package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import com.pm.EnterpriseResourcePlanning.enums.ProductStatus;
import jakarta.validation.constraints.*;

public record ProductRequestDto(
        @NotBlank @Size(min = 5, max = 25)
        String name,
        @NotNull @Positive @Digits(integer = 10, fraction = 2)
        Double price,
        @NotNull @Positive @Digits(integer = 2, fraction = 0)
        Integer unit,
        ProductStatus productStatus
) {
}
