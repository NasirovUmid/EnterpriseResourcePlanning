package com.pm.EnterpriseResourcePlanning.dto.responsdtos;

import com.pm.EnterpriseResourcePlanning.enums.RoleStatus;

import java.util.UUID;

public record RoleResponseDto (
        UUID id,
        String name,
        RoleStatus status
) {
}
