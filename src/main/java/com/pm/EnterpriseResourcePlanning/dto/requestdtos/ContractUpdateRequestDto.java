package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import jakarta.validation.constraints.Positive;

import java.time.Instant;

public record ContractUpdateRequestDto(
        @Positive
        Double amount,
        Instant startDate,
        Instant endDate
) {
}
