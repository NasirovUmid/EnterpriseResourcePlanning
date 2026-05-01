package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import com.pm.EnterpriseResourcePlanning.entity.ProjectEntity;

import java.util.UUID;
import java.util.List;

public interface ContractProjectsRepository {

    void saveContractProjects(UUID contractId, UUID projectId);

    void deleteContractProjects(UUID contractId, UUID projectId);

    boolean exists(UUID contractId, UUID projectId);

    List<ProjectEntity> getProjectsByContractId(UUID contractId);

    List<ContractsEntity> getContractsByProjectId(UUID projectId);
}
