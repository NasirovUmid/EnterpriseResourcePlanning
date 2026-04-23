package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.ProjectOrganizationDao;
import com.pm.EnterpriseResourcePlanning.entity.OrganizationEntity;
import com.pm.EnterpriseResourcePlanning.entity.ProjectEntity;
import com.pm.EnterpriseResourcePlanning.repository.ProjectOrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProjectOrganizationDaoImpl implements ProjectOrganizationDao {

    private final ProjectOrganizationRepository repository;

    @Override
    public void linkProjectOrganization(UUID projectId, UUID organizationId) {
        repository.linkProjectOrganization(projectId, organizationId);
    }

    @Override
    public boolean exists(UUID projectId, UUID organizationId) {
        return repository.exists(projectId, organizationId);
    }

    @Override
    public void removeProjectOrganizationLink(UUID projectId, UUID organizationId) {
        repository.removeProjectOrganizationLink(projectId, organizationId);
    }

    @Override
    public List<OrganizationEntity> getProjectOrganizations(UUID projectId) {
        return repository.getProjectOrganizations(projectId);
    }

    @Override
    public List<ProjectEntity> getOrganizationProjects(UUID organizationId) {
        return repository.getOrganizationProjects(organizationId);
    }
}
