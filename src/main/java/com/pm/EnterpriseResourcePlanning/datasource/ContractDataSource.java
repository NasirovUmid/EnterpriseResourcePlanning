package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ContractResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.UUID;

public interface ContractDataSource {

    ContractResponseDto saveContract(String name, Double amount, Instant startDate, Instant endDate);

    Page<ContractResponseDto> getContractsPage(Specification<ContractsEntity> specification,Pageable pageable);

    void updateContracts(Double amount, Instant startDate, Instant enddate, UUID id);

    void deleteContract(UUID id);

    ContractResponseDto getContractById(UUID id);

    boolean existsByContractNumber(String contractNumber);

}
