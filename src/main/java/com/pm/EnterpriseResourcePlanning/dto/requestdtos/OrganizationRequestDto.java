package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrganizationRequestDto(
        @NotNull @NotBlank
        String name,
        @NotNull @NotBlank
        String inn,
        @NotNull @NotBlank
        String address
) {
}
