package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import com.pm.EnterpriseResourcePlanning.entity.ProjectEntity;

import java.util.List;
import java.util.UUID;

public interface ContractProjectsDao {

    void saveContractProjects(UUID contractId, UUID projectId);

    void deleteContractProjects(UUID contractId, UUID projectId);

    boolean exists(UUID contractId, UUID projectId);

    List<ProjectEntity> getProjectsByContractId(UUID contractId);

    List<ContractsEntity> getContractsByProjectId(UUID projectId);

}
