package com.pm.EnterpriseResourcePlanning.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
public class RolePermissionId implements Serializable {

    private UUID roleId;
    private UUID permissionId;
}
