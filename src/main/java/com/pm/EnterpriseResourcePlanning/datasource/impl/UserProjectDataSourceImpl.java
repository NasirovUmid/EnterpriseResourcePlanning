package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.UserProjectDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.UserProjectDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProjectResponseDto;
import com.pm.EnterpriseResourcePlanning.mapper.ProjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserProjectDataSourceImpl extends MessageAlertDataSource implements UserProjectDataSource {

    private final ProjectMapper mapper;
    private final UserProjectDaoImpl userProjectDao;

    public UserProjectDataSourceImpl(AlertSystemDao alertSystemDao, ProjectMapper mapper, UserProjectDaoImpl userProjectDao) {
        super(alertSystemDao, UserProjectDataSourceImpl.class);
        this.mapper = mapper;
        this.userProjectDao = userProjectDao;
    }

    @Override
    public void saveUserProject(UUID userId, UUID projectId) {
        execute(() -> userProjectDao.saveUserProject(userId, projectId));
    }

    @Override
    public boolean existsUserProject(UUID userId, UUID projectId) {
        return execute(() -> userProjectDao.existsUserProject(userId, projectId));
    }

    @Override
    public void removeUserProject(UUID userId, UUID projectId) {
        execute(() -> userProjectDao.removeUserProject(userId, projectId));
    }

    @Override
    public List<ProjectResponseDto> getUserProjects(UUID userId) {
        return execute(() -> userProjectDao.getUserProjects(userId).stream().map(mapper::toDto).toList());
    }
}
