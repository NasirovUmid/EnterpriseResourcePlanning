package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.ProjectOrganizationDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.ProjectOrganizationDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.OrganizationResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProjectResponseDto;
import com.pm.EnterpriseResourcePlanning.mapper.OrganizationMapper;
import com.pm.EnterpriseResourcePlanning.mapper.ProjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProjectOrganizationDataSourceImpl extends MessageAlertDataSource implements ProjectOrganizationDataSource {

    private final ProjectOrganizationDaoImpl projectOrganizationDao;
    private final ProjectMapper projectMapper;
    private final OrganizationMapper organizationMapper;

    public ProjectOrganizationDataSourceImpl(AlertSystemDao alertSystemDao, ProjectOrganizationDaoImpl projectOrganizationDao, ProjectMapper projectMapper, OrganizationMapper organizationMapper) {
        super(alertSystemDao, ProjectOrganizationDataSourceImpl.class);
        this.projectOrganizationDao = projectOrganizationDao;
        this.projectMapper = projectMapper;
        this.organizationMapper = organizationMapper;
    }

    @Override
    public void linkProjectOrganization(UUID projectId, UUID organizationId) {
        execute(() -> projectOrganizationDao.linkProjectOrganization(projectId, organizationId));
    }

    @Override
    public boolean exists(UUID projectId, UUID organizationId) {
        return execute(() -> projectOrganizationDao.exists(projectId, organizationId));
    }

    @Override
    public void removeProjectOrganizationLink(UUID projectId, UUID organizationId) {
        execute(() -> projectOrganizationDao.removeProjectOrganizationLink(projectId, organizationId));
    }

    @Override
    public List<OrganizationResponseDto> getProjectOrganizations(UUID projectId) {
        return execute(() -> projectOrganizationDao.getProjectOrganizations(projectId).stream().map(organizationMapper::toDto).toList());
    }

    @Override
    public List<ProjectResponseDto> getOrganizationProjects(UUID organizationId) {
        return execute(() -> projectOrganizationDao.getOrganizationProjects(organizationId).stream().map(projectMapper::toDto).toList());
    }
}
