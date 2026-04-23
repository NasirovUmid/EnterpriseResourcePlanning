package com.pm.EnterpriseResourcePlanning.dto.responsdtos;

import com.pm.EnterpriseResourcePlanning.enums.ClientType;

import java.util.UUID;

public record ClientResponseDto(
        UUID id,
        String fullName,
        String phone,
        ClientType type
) {
}
