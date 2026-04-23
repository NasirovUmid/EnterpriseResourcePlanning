package com.pm.EnterpriseResourcePlanning.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "organization_contracts")
public class OrganizationContracts {

    @EmbeddedId
    private OrganizationContractsId organizationContractsId = new OrganizationContractsId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("organizationId")
    @JoinColumn(name = "organization_id")
    private OrganizationEntity organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("contractsId")
    @JoinColumn(name = "contracts_id")
    private ContractsEntity contracts;

}
