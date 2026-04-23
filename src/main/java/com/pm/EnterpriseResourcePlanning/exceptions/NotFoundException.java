package com.pm.EnterpriseResourcePlanning.exceptions;

import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import lombok.Getter;

import java.util.UUID;

@Getter
public class NotFoundException extends RuntimeException {

    private final UUID entityId;
    private final String message;

    public NotFoundException(ErrorMessages error, UUID entityId) {
        super(error.getMessage());
        this.entityId = entityId;
        message = null;
    }

    public NotFoundException(ErrorMessages error, String message) {
        super(error.getMessage());
        this.entityId = null;
        this.message = message;
    }
}
