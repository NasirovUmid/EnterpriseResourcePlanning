package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record LinkRequestDto(
        @NotNull
        UUID entityId,
        @NotNull
        UUID relatedEntityId
) {
}
