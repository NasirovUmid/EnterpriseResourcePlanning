package com.pm.EnterpriseResourcePlanning.dto.filters;

import com.pm.EnterpriseResourcePlanning.enums.UserStatus;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.RequestParam;

public record UserFilterDto(
        @RequestParam(required = false, name = "fullName") @Size(min = 5, max = 25)
        String fullName,
        @RequestParam(required = false, name = "username") @Email @Nullable
        String username,
        @RequestParam(required = false, name = "phoneNumber") @Pattern(regexp = "^(\\+)?998\\d{9}$")
        String phoneNumber,
        @RequestParam(required = false, name = "userStatus")
        UserStatus userStatus,
        @RequestParam(defaultValue = "fullName,desc", name = "sort")
        String sort
) {
}
