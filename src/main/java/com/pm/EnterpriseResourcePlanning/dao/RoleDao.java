package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.RolesEntity;
import com.pm.EnterpriseResourcePlanning.enums.RoleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RoleDao {

    RolesEntity saveRole(String name, RoleStatus status);

    Page<RolesEntity> getRolePages(Pageable pageable);

    void deactivateRole(UUID id);

    RolesEntity getRoleById(UUID id);

}
