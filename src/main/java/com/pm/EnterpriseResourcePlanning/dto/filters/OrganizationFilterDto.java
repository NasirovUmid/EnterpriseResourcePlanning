package com.pm.EnterpriseResourcePlanning.dto.filters;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OrganizationFilterDto(
        @Size(min = 5, max = 25)
        String name,
        @Nullable @NotBlank
        String inn,
        @Nullable
        @NotBlank
        String address
) {
}
