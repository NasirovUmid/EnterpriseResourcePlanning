package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.UUID;

public interface ContractDao {

    ContractsEntity saveContract(String name, Double amount, Instant startDate, Instant endDate);

    Page<ContractsEntity> getContractsPage(Specification<ContractsEntity> specification, Pageable pageable);

    void updateContracts(Double amount, Instant startDate, Instant enddate, UUID id);

    ContractsEntity getContractById(UUID id);

    boolean existsByContractNumber(String contractNumber);
}
