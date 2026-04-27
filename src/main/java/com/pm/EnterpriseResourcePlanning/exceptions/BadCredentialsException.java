package com.pm.EnterpriseResourcePlanning.exceptions;

import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import lombok.Getter;

@Getter
public class BadCredentialsException extends RuntimeException {

    private final String entityEmail;

    public BadCredentialsException(ErrorMessages message, String entityEmail) {
        super(message.getMessage());
        this.entityEmail = entityEmail;
    }
}
