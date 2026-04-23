package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.ContractDao;
import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.exceptions.NotFoundException;
import com.pm.EnterpriseResourcePlanning.repository.ContractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class ContractDaoImpl implements ContractDao {

    private ContractRepository repository;

    @Override
    public ContractsEntity saveContract(String name, Double amount, Instant startDate, Instant endDate) {
        return repository.saveContract(name, amount, startDate, endDate).orElseThrow(RuntimeException::new);
    }

    @Override
    public Page<ContractsEntity> getContractsPage(Specification<ContractsEntity> specification,Pageable pageable) {
        return repository.findAll(specification,pageable);
    }

    @Override
    public void updateContracts(Double amount, Instant startDate, Instant enddate, UUID id) {
         repository.updateContracts(amount, startDate, enddate, id);
    }

    @Override
    public ContractsEntity getContractById(UUID id) {
        return repository.getContractById(id).orElseThrow(() -> new NotFoundException(ErrorMessages.CONTRACT_NOT_FOUND,id));
    }

    @Override
    public boolean existsByContractNumber(String contractNumber) {
        return repository.existsByContractNumber(contractNumber);
    }
}
