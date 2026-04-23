package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.OrganizationContractDao;
import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import com.pm.EnterpriseResourcePlanning.repository.OrganizationContractsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrganizationContractDaoImpl implements OrganizationContractDao {

    private final OrganizationContractsRepository repository;

    @Override
    public void linkOrganizationContract(UUID organizationId, UUID contractId) {
        repository.linkOrganizationContract(organizationId, contractId);
    }

    @Override
    public boolean exists(UUID organizationId, UUID contractId) {
        return repository.exists(organizationId, contractId);
    }

    @Override
    public void removeOrganizationContractLink(UUID organizationId, UUID contractId) {
        repository.removeOrganizationContractLink(organizationId, contractId);
    }

    @Override
    public List<ContractsEntity> getOrganizationContracts(UUID organizationId) {
        return repository.getOrganizationContracts(organizationId);
    }
}
