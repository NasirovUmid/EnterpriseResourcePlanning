package com.pm.EnterpriseResourcePlanning.dto.responsdtos;

import com.pm.EnterpriseResourcePlanning.enums.SalesStatus;

import java.time.Instant;
import java.util.UUID;

public record SalesResponseDto(
        UUID id,
        String contractName,
        Double totalPrice,
        Instant date,
        SalesStatus status
) {
}
