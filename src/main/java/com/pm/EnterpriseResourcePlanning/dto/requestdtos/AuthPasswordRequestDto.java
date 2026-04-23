package com.pm.EnterpriseResourcePlanning.dto.requestdtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthPasswordRequestDto(

        @NotNull @Size(min = 8, max = 45)
        String oldPassword,

        @NotNull @Size(min = 8, max = 45)
        String newPassword
) {
}
