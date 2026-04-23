package com.pm.EnterpriseResourcePlanning.exceptions;

import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
public class IllegalStateException extends RuntimeException {

    private final List<UUID> entityIds;


    public IllegalStateException(ErrorMessages message, UUID... entityIds) {
        super(message.getMessage());
        this.entityIds = (entityIds != null) ? Arrays.asList(entityIds) : null;
    }
}
