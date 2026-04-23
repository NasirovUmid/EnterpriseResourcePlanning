package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.ProjectDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.ProjectDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProjectResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.UserResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ProjectEntity;
import com.pm.EnterpriseResourcePlanning.enums.ProjectStatus;
import com.pm.EnterpriseResourcePlanning.mapper.ProjectMapper;
import com.pm.EnterpriseResourcePlanning.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProjectDataSourceImpl extends MessageAlertDataSource implements ProjectDataSource {

    private final ProjectDaoImpl projectDao;
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;

    public ProjectDataSourceImpl(AlertSystemDao alertSystemDao, ProjectDaoImpl projectDao, ProjectMapper projectMapper, UserMapper userMapper) {
        super(alertSystemDao, ProjectDataSourceImpl.class);
        this.projectDao = projectDao;
        this.projectMapper = projectMapper;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserResponseDto> findUsersByProject(UUID projectId) {
        return execute(() -> projectDao.findUsersByProject(projectId).stream().map(userMapper::toDto).toList());
    }

    @Override
    public Page<ProjectResponseDto> getProjectPage(Specification<ProjectEntity> specification, Pageable pageable) {
        return execute(() -> projectDao.getProjectPage(specification, pageable).map(projectMapper::toDto));
    }

    @Override
    public ProjectResponseDto saveProject(String name, ProjectStatus projectStatus) {
        return execute(() -> projectMapper.toDto(projectDao.saveProject(name, projectStatus)));
    }

    @Override
    public void updateProject(String name, ProjectStatus status, UUID id) {
        execute(() -> projectDao.updateProject(name, status, id));
    }

    @Override
    public ProjectResponseDto getProjectById(UUID id) {
        return execute(() -> projectMapper.toDto(projectDao.getProjectById(id)));
    }
}
