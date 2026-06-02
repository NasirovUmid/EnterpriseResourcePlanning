package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.RolesEntity;
import com.pm.EnterpriseResourcePlanning.entity.UserEntity;

import java.util.List;
import java.util.UUID;


public interface UserRolesRepository {

    void saveUserRole(UUID userId, UUID roleId);

    boolean exists(UUID userId, UUID roleId);

    void removeUserRoleLink(UUID userId, UUID roleId);

    List<RolesEntity> findRolesByUserId(UUID userId);

    List<UserEntity> findUsersByRoleId(UUID roleId);

    List<String> findAllAuthoritiesByUserId(UUID userId);
}
