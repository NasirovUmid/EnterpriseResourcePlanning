package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import com.pm.EnterpriseResourcePlanning.enums.RoleStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RoleRequestDto(
        @NotNull @Size(min = 3, max = 20)
        String name,
        RoleStatus status
) {
}
