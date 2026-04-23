package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PermissionRequestDto(
        @NotNull @NotBlank
        String name,
        UUID moduleId,
        UUID actionId
) {
}
