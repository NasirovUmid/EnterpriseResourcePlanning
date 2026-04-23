package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import jakarta.validation.constraints.Size;

import java.util.UUID;

public record PermissionUpdateRequestDto(
        @Size(min = 5, max = 25)
        String name,
        UUID moduleId,
        UUID actionId
) {
}
