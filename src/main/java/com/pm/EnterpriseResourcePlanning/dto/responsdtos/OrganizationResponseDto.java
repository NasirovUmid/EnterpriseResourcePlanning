package com.pm.EnterpriseResourcePlanning.dto.responsdtos;

import java.util.UUID;

public record OrganizationResponseDto(
        UUID id,
        String name,
        String inn,
        String address
) {
}
