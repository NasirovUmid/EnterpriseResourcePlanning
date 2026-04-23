package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import com.pm.EnterpriseResourcePlanning.enums.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectRequestDto(
        @NotNull @NotBlank
        String name,
        @NotNull @NotBlank
        ProjectStatus status
) {
}
