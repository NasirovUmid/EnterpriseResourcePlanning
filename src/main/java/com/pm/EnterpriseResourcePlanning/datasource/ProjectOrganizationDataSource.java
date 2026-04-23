package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.OrganizationResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProjectResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProjectOrganizationDataSource {

    void linkProjectOrganization(UUID projectId, UUID organizationId);

    boolean exists(UUID projectId, UUID organizationId);

    void removeProjectOrganizationLink(UUID projectId, UUID organizationId);

    List<OrganizationResponseDto> getProjectOrganizations(UUID projectId);

    List<ProjectResponseDto> getOrganizationProjects(UUID organizationId);

}
