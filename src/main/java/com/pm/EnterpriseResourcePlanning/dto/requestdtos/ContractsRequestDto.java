package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import jakarta.validation.constraints.*;

import java.time.Instant;

public record ContractsRequestDto(
        @NotNull @NotBlank
        String contractNumber,
        @Positive
        Double amount,
        @FutureOrPresent
        Instant startDate,
        @Future
        Instant endDate
) {
}
