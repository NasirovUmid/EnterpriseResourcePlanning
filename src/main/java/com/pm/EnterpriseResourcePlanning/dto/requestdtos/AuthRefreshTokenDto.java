package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import jakarta.validation.constraints.NotNull;

public record AuthRefreshTokenDto(
        @NotNull
        String refreshToken
) {
}
