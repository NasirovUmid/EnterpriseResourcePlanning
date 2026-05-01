package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UserUpdateRequestDto(
        @Size(min = 5, max = 25) @Nullable
        String fullName,
        @Pattern(regexp = "^(\\+)?998\\d{9}$") @Nullable
        String phoneNumber) {
}
