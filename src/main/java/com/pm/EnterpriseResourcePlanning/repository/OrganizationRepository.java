package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.OrganizationEntity;
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

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, UUID>, JpaSpecificationExecutor<OrganizationEntity>, CustomOrganizationRepository {

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE organizations SET name = coalesce(:name,name), address = coalesce(:address,address) where id = :id""", nativeQuery = true)
    int updateOrganization(@Param("name") String name, @Param("address") String address, @Param("id") UUID id);

    @Transactional(readOnly = true)
    Page<OrganizationEntity> findAll(Specification<OrganizationEntity> specification, Pageable pageable);

    @Query(value = """
            select oe from OrganizationEntity oe where oe.id = :id""")
    Optional<OrganizationEntity> getOrganizationById(@Param("id") UUID id);

    boolean existsByInn(String inn);
}
