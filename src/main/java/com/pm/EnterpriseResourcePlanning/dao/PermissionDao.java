package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.ActionEntity;
import com.pm.EnterpriseResourcePlanning.entity.ModuleEntity;
import com.pm.EnterpriseResourcePlanning.entity.PermissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface PermissionDao {

    List<String> findAllPermissionNamesByUserId(UUID userId);

    PermissionEntity savePermission(String name, ModuleEntity module, ActionEntity action);

    Page<PermissionEntity> getPermissionPage(Specification<PermissionEntity> specification, Pageable pageable);

    void updatePermission(String name, ModuleEntity module, ActionEntity action, UUID id);

    PermissionEntity getPermissionById(UUID id);


}
