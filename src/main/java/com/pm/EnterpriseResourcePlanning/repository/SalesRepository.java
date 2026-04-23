package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import com.pm.EnterpriseResourcePlanning.entity.SalesEntity;
import com.pm.EnterpriseResourcePlanning.enums.SalesStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SalesRepository extends JpaRepository<SalesEntity, UUID> {

    @Query(value = """
            insert into SalesEntity (contracts,totalPrice,date,status) values (:contract,:totalprice,:date,:status)""")
    Optional<SalesEntity> saveSales(@Param("contracts") ContractsEntity contracts, @Param("totalprice") Double totalprice, @Param("date") Instant date, @Param("status") SalesStatus status);

    @Transactional(readOnly = true)
    Page<SalesEntity> findAll(Pageable pageable);

    @Query(value = """
            select se from SalesEntity se where se.id = :id""")
    Optional<SalesEntity> getSalesById(@Param("id") UUID id);

    @Query(value = """
            update SalesEntity se set se.status = coalesce(:status,se.status) where se.id = :id""")
    void updateSales(@Param("id") UUID id, @Param("status") SalesStatus status);
}
