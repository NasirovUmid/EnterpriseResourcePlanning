package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.RolesEntity;
import com.pm.EnterpriseResourcePlanning.enums.RoleStatus;

import java.util.Optional;

public interface CustomRoleRepository {

    Optional<RolesEntity> saveRole(String name, RoleStatus status);

}
