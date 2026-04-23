package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.ProjectDao;
import com.pm.EnterpriseResourcePlanning.entity.ProjectEntity;
import com.pm.EnterpriseResourcePlanning.entity.UserEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.enums.ProjectStatus;
import com.pm.EnterpriseResourcePlanning.exceptions.NotFoundException;
import com.pm.EnterpriseResourcePlanning.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ProjectDaoImpl implements ProjectDao {

    private final ProjectRepository repository;

    @Override
    public List<UserEntity> findUsersByProject(UUID projectId) {
        return repository.findUsersByProject(projectId);
    }

    @Override
    public Page<ProjectEntity> getProjectPage(Specification<ProjectEntity> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    @Override
    public ProjectEntity saveProject(String name, ProjectStatus projectStatus) {
        return repository.saveProject(name, projectStatus).orElseThrow(RuntimeException::new);
    }

    @Override
    public void updateProject(String name, ProjectStatus status, UUID id) {
        repository.updateProject(name, status, id);
    }

    @Override
    public ProjectEntity getProjectById(UUID id) {
        return repository.getProjectById(id).orElseThrow(() -> new NotFoundException(ErrorMessages.PROJECT_NOT_FOUND, id));
    }
}
