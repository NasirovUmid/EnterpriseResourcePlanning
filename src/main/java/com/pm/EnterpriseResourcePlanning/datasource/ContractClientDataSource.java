package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ContractResponseDto;

import java.util.List;
import java.util.UUID;

public interface ContractClientDataSource {

    void saveContractClient(UUID contractId, UUID clientId,Integer ownershipShare);

    boolean exists(UUID contractId, UUID clientId);

    void removeContractClient(UUID contractId, UUID clientId);

    List<ContractResponseDto> getClientContracts(UUID clientId);

}
