package com.pm.EnterpriseResourcePlanning.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "project_organization")
public class ProjectOrganization {

    @EmbeddedId
    private ProjectOrganizationId projectOrganizationId = new ProjectOrganizationId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("organizationId")
    @JoinColumn(name = "organization_id")
    private OrganizationEntity organization;
}
