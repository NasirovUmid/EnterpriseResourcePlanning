package com.pm.EnterpriseResourcePlanning.dto.responsdtos;

import java.time.Instant;
import java.util.UUID;

public record ContractResponseDto(
        UUID id,
        String contractNumber,
        Double price,
        Instant startDate,
        Instant endDate
) {
}
