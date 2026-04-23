package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import com.pm.EnterpriseResourcePlanning.enums.ClientType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClientRequestDto(
        @NotNull @NotBlank @Size(min = 5, max = 20)
        String fullName,
        @NotBlank @Pattern(regexp = "^(\\+)?998\\d{9}$", message = "A number has to be in the format of (+)998XXXXXXXXX")
        String phone,
        @NotNull
        ClientType type
) {
}
