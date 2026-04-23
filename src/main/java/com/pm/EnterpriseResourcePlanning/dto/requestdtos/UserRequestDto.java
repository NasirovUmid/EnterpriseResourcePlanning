package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import jakarta.validation.constraints.*;

public record UserRequestDto(
        @NotNull @Size(min = 5, max = 25)
        String fullname,
        @Email @NotBlank
        String username,
        @NotNull @Size(min = 8, max = 30)
        String password,
        @NotNull
        @Pattern(regexp = "^(\\+)?998\\d{9}$", message = "A number has to be in the format of (+)998XXXXXXXXX") @NotBlank
        String phoneNumber) {
}
