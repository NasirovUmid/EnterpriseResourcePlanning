package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.OrganizationContractDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.OrganizationContractDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ContractResponseDto;
import com.pm.EnterpriseResourcePlanning.mapper.ContractMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OrganizationContractDataSourceImpl extends MessageAlertDataSource implements OrganizationContractDataSource {

    private final OrganizationContractDaoImpl organizationContractDao;
    private final ContractMapper contractMapper;


    public OrganizationContractDataSourceImpl(AlertSystemDao alertSystemDao, OrganizationContractDaoImpl organizationContractDao, ContractMapper contractMapper) {
        super(alertSystemDao, OrganizationContractDataSourceImpl.class);
        this.organizationContractDao = organizationContractDao;
        this.contractMapper = contractMapper;
    }

    @Override
    public void linkOrganizationContract(UUID organizationId, UUID contractId) {
        execute(() -> organizationContractDao.linkOrganizationContract(organizationId, contractId));
    }

    @Override
    public boolean exists(UUID organizationId, UUID contractId) {
        return execute(() -> organizationContractDao.exists(organizationId, contractId));
    }

    @Override
    public void removeOrganizationContractLink(UUID organizationId, UUID contractId) {
        execute(() -> organizationContractDao.removeOrganizationContractLink(organizationId, contractId));
    }

    @Override
    public List<ContractResponseDto> getOrganizationContracts(UUID organizationId) {
        return execute(() -> organizationContractDao.getOrganizationContracts(organizationId).stream().map(contractMapper::toDto).toList());
    }
}
