package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.OrganizationEntity;
import com.pm.EnterpriseResourcePlanning.entity.ProjectEntity;

import java.util.List;
import java.util.UUID;

public interface ProjectOrganizationDao {

    void linkProjectOrganization(UUID projectId, UUID organizationId);

    boolean exists(UUID projectId, UUID organizationId);

    void removeProjectOrganizationLink(UUID projectId, UUID organizationId);

    List<OrganizationEntity> getProjectOrganizations(UUID projectId);

    List<ProjectEntity> getOrganizationProjects(UUID organizationId);

}
