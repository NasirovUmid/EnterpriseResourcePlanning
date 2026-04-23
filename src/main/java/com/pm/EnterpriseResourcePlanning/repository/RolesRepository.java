package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.RolesEntity;
import com.pm.EnterpriseResourcePlanning.enums.RoleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, UUID> {

    @Query(value = """
            insert into RolesEntity (name,status) values (:name,coalesce(:status,status))""")
    Optional<RolesEntity> saveRole(@Param("name") String name, @Param("status") RoleStatus status);

    @Transactional(readOnly = true)
    Page<RolesEntity> findAll(Pageable pageable);

    @Query(value = """
            update RolesEntity re set re.status = 'DEACTIVATED' where re.id = :id""")
    void deactivateRole(@Param("id") UUID id);

    @Query(value = """
            select re from RolesEntity re where re.id = :id""")
    Optional<RolesEntity> getRoleById(@Param("id")UUID id);
}
