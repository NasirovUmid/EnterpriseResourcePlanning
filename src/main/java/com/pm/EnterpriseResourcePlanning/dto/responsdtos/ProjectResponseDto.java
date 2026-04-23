package com.pm.EnterpriseResourcePlanning.dto.responsdtos;

import com.pm.EnterpriseResourcePlanning.enums.ProjectStatus;

import java.util.UUID;

public record ProjectResponseDto(
        UUID id,
        String name,
        ProjectStatus status
) {
}
