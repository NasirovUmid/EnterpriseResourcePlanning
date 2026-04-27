package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProjectResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserProjectDataSource {

    void saveUserProject(UUID userId, UUID projectId);

    boolean existsUserProject(UUID userId, UUID projectId);

    void removeUserProject(UUID userId, UUID projectId);

    List<ProjectResponseDto> getUserProjects(UUID userId);


}
