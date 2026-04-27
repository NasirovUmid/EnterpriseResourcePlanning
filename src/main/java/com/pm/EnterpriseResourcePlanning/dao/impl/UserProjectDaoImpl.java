package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.UserProjectDao;
import com.pm.EnterpriseResourcePlanning.entity.ProjectEntity;
import com.pm.EnterpriseResourcePlanning.repository.UserProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserProjectDaoImpl implements UserProjectDao {

    private final UserProjectRepository repository;

    @Override
    public void saveUserProject(UUID userId, UUID projectId) {
        repository.saveUserProject(userId, projectId);
    }

    @Override
    public boolean existsUserProject(UUID userId, UUID projectId) {
        return repository.existsUserProject(userId, projectId);
    }

    @Override
    public void removeUserProject(UUID userId, UUID projectId) {
        repository.removeUserProject(userId, projectId);
    }

    @Override
    public List<ProjectEntity> getUserProjects(UUID userId) {
        return repository.getUserProjects(userId);
    }
}
