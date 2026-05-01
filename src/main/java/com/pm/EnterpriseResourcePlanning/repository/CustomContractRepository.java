package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface CustomContractRepository {

    Optional<ContractsEntity> saveContract(String name, Double amount,
                                           Instant startDate, Instant endDate);

}
