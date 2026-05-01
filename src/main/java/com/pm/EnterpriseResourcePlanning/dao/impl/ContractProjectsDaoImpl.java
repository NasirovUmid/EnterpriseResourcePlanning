package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.ContractProjectsDao;
import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import com.pm.EnterpriseResourcePlanning.entity.ProjectEntity;
import com.pm.EnterpriseResourcePlanning.repository.ContractProjectsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ContractProjectsDaoImpl implements ContractProjectsDao {

    private final ContractProjectsRepository contractProjectsRepository;

    @Override
    public void saveContractProjects(UUID contractId, UUID projectId) {
        contractProjectsRepository.saveContractProjects(contractId, projectId);
    }

    @Override
    public void deleteContractProjects(UUID contractId, UUID projectId) {
        contractProjectsRepository.deleteContractProjects(contractId, projectId);
    }

    @Override
    public boolean exists(UUID contractId, UUID projectId) {
        return contractProjectsRepository.exists(contractId, projectId);
    }

    @Override
    public List<ProjectEntity> getProjectsByContractId(UUID contractId) {
        return contractProjectsRepository.getProjectsByContractId(contractId);
    }

    @Override
    public List<ContractsEntity> getContractsByProjectId(UUID projectId) {
        return contractProjectsRepository.getContractsByProjectId(projectId);
    }
}
