package com.pm.EnterpriseResourcePlanning.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class UserRolesId implements Serializable {

    private UUID userId;
    private UUID roleId;

}
