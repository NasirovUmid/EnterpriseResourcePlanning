package com.pm.EnterpriseResourcePlanning.dto.filters;

import com.pm.EnterpriseResourcePlanning.enums.ClientType;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClientFilterDto(
        @Size(min = 5, max = 25)
        String fullname,
        @Pattern(regexp = "^(\\+)?998\\d{9}$")
        String phone,
        ClientType type
) {
}
