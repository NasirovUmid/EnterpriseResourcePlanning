package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.RolesEntity;
import com.pm.EnterpriseResourcePlanning.enums.RoleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface RoleDao {

    RolesEntity saveRole(String name, RoleStatus status);

    List<RolesEntity> getRolePages();

    void deactivateRole(UUID id);

    RolesEntity getRoleById(UUID id);
}
