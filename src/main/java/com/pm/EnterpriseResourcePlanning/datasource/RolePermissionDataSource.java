package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.PermissionResponseDto;

import java.util.List;
import java.util.UUID;

public interface RolePermissionDataSource {

    void saveRolePermissions(UUID roleId, UUID permissionId);

    boolean exists(UUID roleId, UUID permissionId);

    void removeUserRoleLink(UUID roleId, UUID permissionId);

    List<PermissionResponseDto> getRolePermissions(UUID roleId);
}
