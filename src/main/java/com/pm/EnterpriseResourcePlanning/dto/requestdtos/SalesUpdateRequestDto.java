package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import com.pm.EnterpriseResourcePlanning.enums.SalesStatus;
import jakarta.validation.constraints.NotNull;

public record SalesUpdateRequestDto(
        @NotNull
        SalesStatus status
) {
}
