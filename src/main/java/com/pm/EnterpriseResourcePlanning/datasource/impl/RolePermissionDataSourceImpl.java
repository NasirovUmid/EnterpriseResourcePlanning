package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.RolePermissionDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.RolePermissionDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.PermissionResponseDto;
import com.pm.EnterpriseResourcePlanning.mapper.PermissionMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class RolePermissionDataSourceImpl extends MessageAlertDataSource implements RolePermissionDataSource {

    private final RolePermissionDaoImpl rolePermissionDao;
    private final PermissionMapper permissionMapper;

    public RolePermissionDataSourceImpl(AlertSystemDao alertSystemDao, RolePermissionDaoImpl rolePermissionDao, PermissionMapper permissionMapper) {
        super(alertSystemDao, RolePermissionDataSourceImpl.class);
        this.rolePermissionDao = rolePermissionDao;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public void saveRolePermissions(UUID roleId, UUID permissionId) {
        execute(() -> rolePermissionDao.saveRolePermissions(roleId, permissionId));
    }

    @Override
    public boolean exists(UUID roleId, UUID permissionId) {
        return execute(() -> rolePermissionDao.exists(roleId, permissionId));
    }

    @Override
    public void removeUserRoleLink(UUID roleId, UUID permissionId) {
        execute(() -> rolePermissionDao.removeUserRoleLink(roleId, permissionId));
    }

    @Override
    public List<PermissionResponseDto> getRolePermissions(UUID roleId) {
        return execute(() -> rolePermissionDao.getRolePermissions(roleId).stream().map(permissionMapper::toDto).toList());
    }
}
