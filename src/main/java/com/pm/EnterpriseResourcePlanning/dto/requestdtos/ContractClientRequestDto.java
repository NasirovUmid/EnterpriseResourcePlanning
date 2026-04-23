package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record ContractClientRequestDto(
        @NotNull
        UUID uuid,
        @NotNull
        UUID uuid1,
        @NotNull @Positive @Digits(integer = 2, fraction = 0)
        Integer ownershipShare
) {
}
