package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.RoleDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.RoleDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.RoleResponseDto;
import com.pm.EnterpriseResourcePlanning.enums.RoleStatus;
import com.pm.EnterpriseResourcePlanning.mapper.RoleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RolesDataSourceImpl extends MessageAlertDataSource implements RoleDataSource {

    private final RoleDaoImpl roleDao;
    private final RoleMapper roleMapper;

    public RolesDataSourceImpl(AlertSystemDao alertSystemDao, RoleDaoImpl roleDao, RoleMapper roleMapper) {
        super(alertSystemDao, RolesDataSourceImpl.class);
        this.roleDao = roleDao;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleResponseDto saveRole(String name, RoleStatus status) {
        return execute(() -> roleMapper.toDto(roleDao.saveRole(name, status)));
    }

    @Override
    public Page<RoleResponseDto> getRolePages(Pageable pageable) {
        return execute(() -> roleDao.getRolePages(pageable).map(roleMapper::toDto));
    }

    @Override
    public void deactivateRole(UUID id) {
        execute(() -> roleDao.deactivateRole(id));
    }

    @Override
    public RoleResponseDto getRoleById(UUID id) {
        return execute(() -> roleMapper.toDto(roleDao.getRoleById(id)));
    }
}
