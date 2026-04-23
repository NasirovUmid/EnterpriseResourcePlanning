package com.pm.EnterpriseResourcePlanning.exceptions;

import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import lombok.Getter;

import java.util.*;

@Getter
public class AlreadyExistsException extends RuntimeException {

    private final List<UUID> entityIds;
    private final String value;

    public AlreadyExistsException(ErrorMessages message, UUID... entityId) {
        super(message.getMessage());
        this.entityIds = (entityId != null) ? Arrays.asList(entityId) : null;
        this.value = null;
    }

    public AlreadyExistsException(ErrorMessages message, String value) {
        super(message.getMessage());
        this.entityIds = null;
        this.value = value;
    }
}
