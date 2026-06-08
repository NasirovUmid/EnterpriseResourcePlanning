package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.SalesEntity;
import com.pm.EnterpriseResourcePlanning.enums.SalesStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SalesRepository extends JpaRepository<SalesEntity, UUID>, CustomSalesRepository {

    @Transactional(readOnly = true)
    Page<SalesEntity> findAll(Pageable pageable, Specification<SalesEntity> specification);

    Optional<SalesEntity> findSalesEntityById(UUID id);

    @Modifying
    @Query(value = """
            update SalesEntity se set se.status = :status where se.id = :id""")
    void updateSales(@Param("id") UUID id, @Param("status") SalesStatus status);
}
