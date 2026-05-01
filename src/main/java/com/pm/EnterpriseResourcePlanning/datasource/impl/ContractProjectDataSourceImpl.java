package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.ContractProjectsDao;
import com.pm.EnterpriseResourcePlanning.datasource.ContractProjectDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ContractResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProjectResponseDto;
import com.pm.EnterpriseResourcePlanning.mapper.ContractMapper;
import com.pm.EnterpriseResourcePlanning.mapper.ProjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ContractProjectDataSourceImpl extends MessageAlertDataSource implements ContractProjectDataSource {

    private final ContractProjectsDao contractProjectsDao;
    private final ProjectMapper projectMapper;
    private final ContractMapper contractMapper;

    public ContractProjectDataSourceImpl(AlertSystemDao alertSystemDao, ContractProjectsDao contractProjectsDao, ProjectMapper projectMapper, ContractMapper contractMapper) {
        super(alertSystemDao, ContractProjectDataSourceImpl.class);
        this.contractProjectsDao = contractProjectsDao;
        this.projectMapper = projectMapper;
        this.contractMapper = contractMapper;
    }

    @Override
    public void saveContractProjects(UUID contractId, UUID projectId) {
        execute(() -> contractProjectsDao.saveContractProjects(contractId, projectId));
    }

    @Override
    public void deleteContractProjects(UUID contractId, UUID projectId) {
        execute(() -> contractProjectsDao.deleteContractProjects(contractId, projectId));
    }

    @Override
    public boolean exists(UUID contractId, UUID projectId) {
        return contractProjectsDao.exists(contractId, projectId);
    }

    @Override
    public List<ProjectResponseDto> getProjectsByContractId(UUID contractId) {
        return execute(() -> contractProjectsDao.getProjectsByContractId(contractId).stream().map(projectMapper::toDto).toList());
    }

    @Override
    public List<ContractResponseDto> getContractsByProjectId(UUID projectId) {
        return execute(() -> contractProjectsDao.getContractsByProjectId(projectId).stream().map(contractMapper::toDto).toList());
    }
}
