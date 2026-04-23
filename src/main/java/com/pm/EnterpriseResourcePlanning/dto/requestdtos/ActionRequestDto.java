package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActionRequestDto(
        @NotNull @NotBlank
        String name
) {
}
