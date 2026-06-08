package com.pm.EnterpriseResourcePlanning.dto.filters;

import com.pm.EnterpriseResourcePlanning.enums.SalesStatus;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

public record SalesFilterDto(
        @Positive
        Double greaterThan,
        @Positive
        Double lowerThan,
        @PastOrPresent
        Instant date,
        SalesStatus status) {
}
