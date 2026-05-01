package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.ActionDaoImpl;
import com.pm.EnterpriseResourcePlanning.dao.impl.ModuleDaoImpl;
import com.pm.EnterpriseResourcePlanning.dao.impl.PermissionDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.PermissionDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.PermissionResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ActionEntity;
import com.pm.EnterpriseResourcePlanning.entity.ModuleEntity;
import com.pm.EnterpriseResourcePlanning.entity.PermissionEntity;
import com.pm.EnterpriseResourcePlanning.mapper.PermissionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PermissionDataSourceImpl extends MessageAlertDataSource implements PermissionDataSource {

    private final PermissionDaoImpl permissionDao;
    private final PermissionMapper permissionMapper;
    private final ModuleDaoImpl moduleDao;
    private final ActionDaoImpl actionDao;

    public PermissionDataSourceImpl(AlertSystemDao alertSystemDao, PermissionDaoImpl permissionDao, PermissionMapper permissionMapper, ModuleDaoImpl moduleDao, ActionDaoImpl actionDao) {
        super(alertSystemDao, PermissionDataSourceImpl.class);
        this.permissionDao = permissionDao;
        this.permissionMapper = permissionMapper;
        this.moduleDao = moduleDao;
        this.actionDao = actionDao;
    }

    @Override
    public List<String> findAllPermissionNamesByUserId(UUID userId) {
        return execute(() -> permissionDao.findAllPermissionNamesByUserId(userId));
    }

    @Override
    public PermissionResponseDto savePermission(String name, UUID moduleId, UUID actionId) {

        return execute(() -> permissionMapper.toDto(permissionDao.savePermission(name, moduleId, actionId)));
    }

    @Override
    public Page<PermissionResponseDto> getPermissionPage(Specification<PermissionEntity> specification, Pageable pageable) {
        return execute(() -> permissionDao.getPermissionPage(specification, pageable).map(permissionMapper::toDto));
    }

    @Override
    public void updatePermission(String name, UUID moduleId, UUID actionId, UUID id) {


        execute(() -> permissionDao.updatePermission(name, moduleId, actionId, id));
    }

    @Override
    public PermissionResponseDto getPermissionById(UUID id) {
        return execute(() -> permissionMapper.toDto(permissionDao.getPermissionById(id)));
    }
}
