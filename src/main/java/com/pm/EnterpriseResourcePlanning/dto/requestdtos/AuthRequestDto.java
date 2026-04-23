package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthRequestDto(
        @Email @NotNull @NotBlank
        String email,
        @NotNull @Size(min = 8, max = 20)
        String password
) {
}
