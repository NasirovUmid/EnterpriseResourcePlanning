package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<ContractsEntity, UUID>, JpaSpecificationExecutor<ContractsEntity>, CustomContractRepository {

    @Transactional(readOnly = true)
    Page<ContractsEntity> findAll(Specification<ContractsEntity> specification, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE contracts SET amount = coalesce(:amount,amount), start_date = coalesce(:startdate,start_date),
                                             end_date = coalesce(:enddate,end_date) where id = :id""", nativeQuery = true)
    int updateContracts(@Param("amount") Double amount, @Param("startdate") Instant startDate, @Param("enddate") Instant enddate, @Param("id") UUID id);


    Optional<ContractsEntity> findContractsEntityById(UUID id);

    boolean existsByContractNumber(String contractNumber);
}
