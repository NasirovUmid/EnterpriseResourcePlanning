package com.pm.EnterpriseResourcePlanning.dto.responsdtos;

import java.util.UUID;

public record AuthUserResponseDto(
        UUID userId,
        String accessToken,
        String refreshToken
) {
}
