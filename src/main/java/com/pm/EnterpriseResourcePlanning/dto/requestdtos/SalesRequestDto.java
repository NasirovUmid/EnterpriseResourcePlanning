package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import com.pm.EnterpriseResourcePlanning.enums.SalesStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;
import java.util.UUID;

public record SalesRequestDto(
        @NotNull
        UUID contractsId,
        @Positive @NotNull
        Double totalPrice,
        @Future @NotNull
        Instant date,
        @NotNull
        SalesStatus status
) {
}
