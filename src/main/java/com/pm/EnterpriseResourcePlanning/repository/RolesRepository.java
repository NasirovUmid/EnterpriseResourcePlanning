package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.RolesEntity;
import com.pm.EnterpriseResourcePlanning.enums.RoleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, UUID>, CustomRoleRepository {

    @Transactional(readOnly = true)
    List<RolesEntity> findAll();

    @Modifying
    @Query(value = """
            update RolesEntity re set re.status = 'DEACTIVATED' where re.id = :id""")
    void deactivateRole(@Param("id") UUID id);

    Optional<RolesEntity> findRolesEntityById(UUID id);

}
