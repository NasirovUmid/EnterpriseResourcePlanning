package com.pm.EnterpriseResourcePlanning.entity;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class ProjectOrganizationId {

    private UUID projectId;
    private UUID organizationId;
}
