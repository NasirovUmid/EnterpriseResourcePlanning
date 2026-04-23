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
public interface ContractRepository extends JpaRepository<ContractsEntity, UUID>, JpaSpecificationExecutor<ContractsEntity> {

    @Query(value = """
            INSERT INTO ContractsEntity (contractNumber, amount, startDate, endDate) 
            VALUES (:contractnumber,:amount,:startdate,:enddate)""")
    Optional<ContractsEntity> saveContract(@Param("contractnumber") String name, @Param("amount") Double amount,
                     @Param("startdate") Instant startDate, @Param("enddate") Instant endDate);

    @Transactional(readOnly = true)
    Page<ContractsEntity> findAll(Specification<ContractsEntity> specification,Pageable pageable);

    @Modifying
    @Query(value = """
            UPDATE contracts SET amount = coalesce(:amount,amount), start_date = coalesce(:startsate,start_date),
                                             end_date = coalesce(:enddate,end_date) where id = :id""", nativeQuery = true)
    int updateContracts(@Param("amount") Double amount, @Param("startdate") Instant startDate, @Param("enddate") Instant enddate, @Param("id") UUID id);

    @Query(value = """
            select ce from ContractsEntity ce where ce.id = :id""")
    Optional<ContractsEntity> getContractById(@Param("id") UUID id);

    boolean existsByContractNumber(String contractNumber);
}
