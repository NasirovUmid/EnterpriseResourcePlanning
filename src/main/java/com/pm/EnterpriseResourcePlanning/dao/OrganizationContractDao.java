package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;

import java.util.List;
import java.util.UUID;

public interface OrganizationContractDao {

    void linkOrganizationContract(UUID organizationId, UUID contractId);

    boolean exists(UUID organizationId, UUID contractId);

    void removeOrganizationContractLink(UUID organizationId, UUID contractId);

    List<ContractsEntity> getOrganizationContracts(UUID organizationId);

}
