package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.RolePermissionDao;
import com.pm.EnterpriseResourcePlanning.entity.PermissionEntity;
import com.pm.EnterpriseResourcePlanning.repository.impl.RolePermissionRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class RolePermissionDaoImpl implements RolePermissionDao {

    private final RolePermissionRepositoryImpl rolePermissionRepository;

    @Override
    public void saveRolePermissions(UUID roleId, UUID permissionId) {
        rolePermissionRepository.saveRolePermissions(roleId, permissionId);
    }

    @Override
    public boolean exists(UUID roleId, UUID permissionId) {
        return rolePermissionRepository.exists(roleId, permissionId);
    }

    @Override
    public void removeUserRoleLink(UUID roleId, UUID permissionId) {
        rolePermissionRepository.removeUserRoleLink(roleId, permissionId);
    }

    @Override
    public List<PermissionEntity> getRolePermissions(UUID roleId) {
        return rolePermissionRepository.getRolePermissions(roleId);
    }
}
