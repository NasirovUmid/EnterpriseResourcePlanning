package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.ActionDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.ActionDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ActionResponseDto;
import com.pm.EnterpriseResourcePlanning.mapper.ActionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ActionDataSourceImpl extends MessageAlertDataSource implements ActionDataSource {

    private final ActionDaoImpl actionDao;
    private final ActionMapper actionMapper;

    public ActionDataSourceImpl(AlertSystemDao alertSystemDao, ActionDaoImpl actionDao, ActionMapper actionMapper) {
        super(alertSystemDao, ActionDataSourceImpl.class);
        this.actionDao = actionDao;
        this.actionMapper = actionMapper;
    }

    @Override
    public ActionResponseDto saveAction(String name) {
        return execute(() -> actionMapper.toDto(actionDao.saveAction(name)));
    }

    @Override
    public Page<ActionResponseDto> getActionPage(Pageable pageable) {
        return execute(() -> actionDao.getActionPage(pageable).map(actionMapper::toDto));
    }

    @Override
    public void updateAction(String name, UUID id) {
        execute(() -> actionDao.updateAction(name, id));
    }

    @Override
    public ActionResponseDto getActionById(UUID id) {
        return execute(() -> actionMapper.toDto(actionDao.getActionById(id)));
    }
}
