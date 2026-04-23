package com.pm.EnterpriseResourcePlanning.entity;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class OrganizationContractsId {

    private UUID organizationId;
    private UUID contractsId;

}
