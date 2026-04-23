package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import com.pm.EnterpriseResourcePlanning.enums.ProjectStatus;
import jakarta.validation.constraints.Size;

public record ProjectUpdateRequestDto(
        @Size(min = 5, max = 25)
        String name,
        ProjectStatus status
) {
}
