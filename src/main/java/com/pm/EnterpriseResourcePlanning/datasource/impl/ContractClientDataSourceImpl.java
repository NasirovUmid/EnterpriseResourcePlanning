package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.ContractClientDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.ContractClientDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ContractResponseDto;
import com.pm.EnterpriseResourcePlanning.mapper.ContractMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ContractClientDataSourceImpl extends MessageAlertDataSource implements ContractClientDataSource {

    private final ContractClientDaoImpl contractClientDao;
    private final ContractMapper contractMapper;

    public ContractClientDataSourceImpl(AlertSystemDao alertSystemDao, ContractClientDaoImpl contractClientDao, ContractMapper contractMapper) {
        super(alertSystemDao, ContractClientDataSourceImpl.class);
        this.contractClientDao = contractClientDao;
        this.contractMapper = contractMapper;
    }

    @Override
    public void saveContractClient(UUID contractId, UUID clientId,Integer ownershipShare) {
        execute(() -> contractClientDao.saveContractClient(contractId, clientId,ownershipShare));
    }

    @Override
    public boolean exists(UUID contractId, UUID clientId) {
        return execute(() -> contractClientDao.exists(contractId, clientId));
    }

    @Override
    public void removeContractClient(UUID contractId, UUID clientId) {
        execute(() -> contractClientDao.removeContractClient(contractId, clientId));
    }

    @Override
    public List<ContractResponseDto> getClientContracts(UUID clientId) {
        return execute(() -> contractClientDao.getClientContracts(clientId).stream().map(contractMapper::toDto).toList());
    }
}
