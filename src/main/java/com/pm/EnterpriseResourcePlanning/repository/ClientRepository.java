package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.ClientEntity;
import com.pm.EnterpriseResourcePlanning.enums.ClientType;
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
public interface ClientRepository extends JpaRepository<ClientEntity, UUID>, JpaSpecificationExecutor<ClientEntity>, CustomClientRepository {

    @Transactional(readOnly = true)
    Page<ClientEntity> findAll(Specification<ClientEntity> specification, Pageable pageable);

    @Modifying
    @Query(value = """
            UPDATE clients SET full_name = coalesce(:fullname,full_name) , phone = coalesce(:phone,phone) where id = :id""", nativeQuery = true)
    int updateClient(@Param("fullname") String fullname, @Param("phone") String phone, @Param("id") UUID id);

    Optional<ClientEntity> findClientEntityById(UUID id);
}
