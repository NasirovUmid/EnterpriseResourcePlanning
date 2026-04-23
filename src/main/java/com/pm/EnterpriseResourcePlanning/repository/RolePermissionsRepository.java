package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.PermissionEntity;

import java.util.List;
import java.util.UUID;

public interface RolePermissionsRepository {

    void saveRolePermissions(UUID roleId, UUID permissionId);

    boolean exists(UUID roleId, UUID permissionId);

    void removeUserRoleLink(UUID roleId, UUID permissionId);

    List<PermissionEntity> getRolePermissions(UUID roleId);
}
