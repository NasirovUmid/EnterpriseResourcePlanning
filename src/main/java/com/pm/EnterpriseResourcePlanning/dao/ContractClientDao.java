package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;

import java.util.List;
import java.util.UUID;

public interface ContractClientDao {

    void saveContractClient(UUID contractId, UUID clientId,Integer ownershipShare);

    boolean exists(UUID contractId, UUID clientId);

    void removeContractClient(UUID contractId, UUID clientId);

    List<ContractsEntity> getClientContracts(UUID clientId);


}
