package com.pm.EnterpriseResourcePlanning.exceptions;

import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;

public class MethodArgumentNotValidException extends RuntimeException {

    public MethodArgumentNotValidException(ErrorMessages message) {
        super(message.getMessage());
    }
}
