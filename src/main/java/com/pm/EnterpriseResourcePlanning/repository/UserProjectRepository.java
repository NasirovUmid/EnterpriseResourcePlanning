package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.ProjectEntity;

import java.util.List;
import java.util.UUID;

public interface UserProjectRepository {

    void saveUserProject(UUID userId, UUID projectId);

    boolean existsUserProject(UUID userId, UUID projectId);

    void removeUserProject(UUID userId, UUID projectId);

    List<ProjectEntity> getUserProjects(UUID userId);
}
