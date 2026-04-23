package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.ModuleDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.ModuleDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ModuleResponseDto;
import com.pm.EnterpriseResourcePlanning.mapper.ModuleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ModuleDataSourceImpl extends MessageAlertDataSource implements ModuleDataSource {

    private final ModuleDaoImpl moduleDao;
    private final ModuleMapper moduleMapper;

    public ModuleDataSourceImpl(AlertSystemDao alertSystemDao, ModuleDaoImpl moduleDao, ModuleMapper moduleMapper) {
        super(alertSystemDao, ModuleDataSourceImpl.class);
        this.moduleDao = moduleDao;
        this.moduleMapper = moduleMapper;
    }

    @Override
    public ModuleResponseDto saveModule(String name) {
        return execute(() -> moduleMapper.toDto(moduleDao.saveModule(name)));
    }

    @Override
    public Page<ModuleResponseDto> getModuleEntities(Pageable pageable) {
        return execute(() -> moduleDao.getModuleEntities(pageable).map(moduleMapper::toDto));
    }

    @Override
    public void updateModule(String name, UUID id) {
        execute(() -> moduleDao.updateModule(name, id));
    }

    @Override
    public ModuleResponseDto getModuleById(UUID id) {
        return execute(() -> moduleMapper.toDto(moduleDao.getModuleById(id)));
    }
}
