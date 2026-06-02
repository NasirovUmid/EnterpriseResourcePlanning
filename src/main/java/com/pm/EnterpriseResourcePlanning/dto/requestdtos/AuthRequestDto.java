package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

public record AuthRequestDto(
        @Email  @NotBlank
        String email,
        @NotNull @Size(min = 8, max = 20)
        String password
) {
}
