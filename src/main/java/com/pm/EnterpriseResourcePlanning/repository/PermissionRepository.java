package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.ActionEntity;
import com.pm.EnterpriseResourcePlanning.entity.ModuleEntity;
import com.pm.EnterpriseResourcePlanning.entity.PermissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<PermissionEntity, UUID>, JpaSpecificationExecutor<PermissionEntity> {

    @Query(value = """
            SELECT DISTINCT p.name FROM permissions p
            JOIN role_permissions rp ON p.id = rp.permission_id
            JOIN user_roles ur ON rp.role_id = ur.role_id
            WHERE ur.user_id = :userId
            """, nativeQuery = true)
    List<String> findAllPermissionNamesByUserId(@Param("userId") UUID userId);

    @Query(value = """
            insert into PermissionEntity (name,moduleId,actionId) values (:name,:module,:action)""")
    Optional<PermissionEntity> savePermission(@Param("name") String name, @Param("module") UUID module, @Param("action") UUID action);

    @Transactional(readOnly = true)
    Page<PermissionEntity> findAll(Specification<PermissionEntity> specification, Pageable pageable);

    @Modifying
    @Query(value = """
            update PermissionEntity pe set pe.name = :name, pe.moduleId = :module , pe.actionId = :action where pe.id = :id""")
    void updatePermission(@Param("name") String name, @Param("module") UUID moduleId,@Param("action") UUID actionId, @Param("id") UUID id);


    Optional<PermissionEntity> findPermissionEntityById(UUID id);
}
