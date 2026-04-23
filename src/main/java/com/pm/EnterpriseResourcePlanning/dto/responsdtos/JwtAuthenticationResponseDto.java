package com.pm.EnterpriseResourcePlanning.dto.responsdtos;

import lombok.Builder;

@Builder
public record JwtAuthenticationResponseDto(
        String accessToken,
        String refreshToken
) {
}
