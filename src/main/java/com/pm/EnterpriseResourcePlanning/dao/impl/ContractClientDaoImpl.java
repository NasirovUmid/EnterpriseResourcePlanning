package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.ContractClientDao;
import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import com.pm.EnterpriseResourcePlanning.repository.ContractClientsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ContractClientDaoImpl implements ContractClientDao {

    private final ContractClientsRepository repository;

    @Override
    public void saveContractClient(UUID contractId, UUID clientId,Integer ownershipShare) {
        repository.saveContractClient(contractId, clientId,ownershipShare);
    }

    @Override
    public boolean exists(UUID contractId, UUID clientId) {
        return repository.exists(contractId, clientId);
    }

    @Override
    public void removeContractClient(UUID contractId, UUID clientId) {
        repository.removeContractClient(contractId, clientId);
    }

    @Override
    public List<ContractsEntity> getClientContracts(UUID clientId) {
        return repository.getClientContracts(clientId);
    }
}
