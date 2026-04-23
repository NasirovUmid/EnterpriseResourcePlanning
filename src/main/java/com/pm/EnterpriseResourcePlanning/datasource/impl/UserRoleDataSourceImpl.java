package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.UserRolesDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.UserRoleDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.RoleResponseDto;
import com.pm.EnterpriseResourcePlanning.mapper.RoleMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserRoleDataSourceImpl extends MessageAlertDataSource implements UserRoleDataSource {

    private final RoleMapper roleMapper;
    private final UserRolesDaoImpl userRolesDao;

    public UserRoleDataSourceImpl(AlertSystemDao alertSystemDao, RoleMapper roleMapper, UserRolesDaoImpl userRolesDao) {
        super(alertSystemDao, UserRoleDataSourceImpl.class);
        this.roleMapper = roleMapper;
        this.userRolesDao = userRolesDao;
    }

    @Override
    public void saveUserRole(UUID userId, UUID roleId) {
        execute(() -> userRolesDao.saveUserRole(userId, roleId));
    }

    @Override
    public boolean exists(UUID userId, UUID roleId) {
        return execute(() -> userRolesDao.exists(userId, roleId));
    }

    @Override
    public void removeUserRoleLink(UUID userId, UUID roleId) {
        execute(() -> userRolesDao.removeUserRoleLink(userId, roleId));
    }

    @Override
    public List<RoleResponseDto> findRolesByUserId(UUID userId) {
        return execute(() -> userRolesDao.findRolesByUserId(userId).stream().map(roleMapper::toDto).toList());
    }
}
