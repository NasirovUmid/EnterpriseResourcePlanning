package com.pm.EnterpriseResourcePlanning.dto.responsdtos;

import java.time.Instant;
import java.util.UUID;

public record SalesResponseDto(
        UUID id,
        String contractName,
        Double totalPrice,
        Instant date
) {
}
