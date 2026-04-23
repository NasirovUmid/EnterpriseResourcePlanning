package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record IntermediateRequestDto(
        @NotNull
        UUID uuid,
        @NotNull
        UUID uuid1
) {
}
