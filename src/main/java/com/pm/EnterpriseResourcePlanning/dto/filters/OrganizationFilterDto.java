package com.pm.EnterpriseResourcePlanning.dto.filters;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record OrganizationFilterDto(
        @Size(min = 5, max = 25)
        String name,
        @Nullable @Size(min = 9, max = 9) @Pattern(regexp = "^\\S{9}$")
        String inn,
        @Nullable @Pattern(regexp = "^\\S{9}$")
        String address
) {
}
