package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.ContractDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.ContractDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ContractResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import com.pm.EnterpriseResourcePlanning.mapper.ContractMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class ContractDataSourceImpl extends MessageAlertDataSource implements ContractDataSource {

    private final ContractDaoImpl contractDao;
    private final ContractMapper contractMapper;

    public ContractDataSourceImpl(AlertSystemDao alertSystemDao, ContractDaoImpl contractDao, ContractMapper contractMapper) {
        super(alertSystemDao, ContractDataSourceImpl.class);
        this.contractDao = contractDao;
        this.contractMapper = contractMapper;
    }

    @Override
    public ContractResponseDto saveContract(String name, Double amount, Instant startDate, Instant endDate) {
        return execute(() -> contractMapper.toDto(contractDao.saveContract(name, amount, startDate, endDate)));
    }

    @Override
    public Page<ContractResponseDto> getContractsPage(Specification<ContractsEntity> specification, Pageable pageable) {
        return execute(() -> contractDao.getContractsPage(specification, pageable).map(contractMapper::toDto));
    }

    @Override
    public void updateContracts(Double amount, Instant startDate, Instant enddate, UUID id) {
        execute(() -> contractDao.updateContracts(amount, startDate, enddate, id));
    }

    @Override
    public ContractResponseDto getContractById(UUID id) {
        return execute(() -> contractMapper.toDto(contractDao.getContractById(id)));
    }

    @Override
    public boolean existsByContractNumber(String contractNumber) {
        return contractDao.existsByContractNumber(contractNumber);
    }
}
