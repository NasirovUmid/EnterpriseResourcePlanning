package com.pm.EnterpriseResourcePlanning.dto.filters;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

public record ContractFilterDto(
        @Nullable @NotBlank
        String contractNumber,
        @Positive
        Double amountGreater,
        @Positive
        Double amountLower,
        Instant startDateFrom,
        Instant startDateTo,
        Instant endDateFrom,
        Instant endDateTo
) {
}
