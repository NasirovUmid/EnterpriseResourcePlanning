package com.pm.EnterpriseResourcePlanning.dto.responsdtos;

import java.util.UUID;

public record PermissionResponseDto(
        UUID id,
        String name,
        String moduleName,
        String actionName
) {
}
