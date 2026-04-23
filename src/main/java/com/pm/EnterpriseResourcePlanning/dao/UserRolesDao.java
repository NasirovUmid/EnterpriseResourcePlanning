package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.RolesEntity;

import java.util.List;
import java.util.UUID;

public interface UserRolesDao {

    void saveUserRole(UUID userId, UUID roleId);

    boolean exists(UUID userId, UUID roleId);

    void removeUserRoleLink(UUID userId, UUID roleId);

    List<RolesEntity> findRolesByUserId(UUID userId);


}
