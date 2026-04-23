package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ContractResponseDto;

import java.util.List;
import java.util.UUID;

public interface OrganizationContractDataSource {

    void linkOrganizationContract(UUID organizationId, UUID contractId);

    boolean exists(UUID organizationId, UUID contractId);

    void removeOrganizationContractLink(UUID organizationId, UUID contractId);

    List<ContractResponseDto> getOrganizationContracts(UUID organizationId);

}
