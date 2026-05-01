package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ContractResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProjectResponseDto;

import java.util.List;
import java.util.UUID;

public interface ContractProjectDataSource {

    void saveContractProjects(UUID contractId, UUID projectId);

    void deleteContractProjects(UUID contractId, UUID projectId);

    boolean exists(UUID contractId, UUID projectId);

    List<ProjectResponseDto> getProjectsByContractId(UUID contractId);

    List<ContractResponseDto> getContractsByProjectId(UUID projectId);

}
