package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OrganizationUpdateRequestDto(
        @Size(min = 5, max = 25)
        String name,
        @NotBlank
        String address
) {
}
