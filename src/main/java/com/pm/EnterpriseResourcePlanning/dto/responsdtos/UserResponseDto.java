package com.pm.EnterpriseResourcePlanning.dto.responsdtos;

import com.pm.EnterpriseResourcePlanning.enums.UserStatus;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String fullName,
        String username,
        String phone,
        UserStatus status
) {
}
