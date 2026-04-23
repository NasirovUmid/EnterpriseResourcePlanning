package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProjectResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.UserResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ProjectEntity;
import com.pm.EnterpriseResourcePlanning.enums.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface ProjectDataSource {

    List<UserResponseDto> findUsersByProject(UUID projectId);

    Page<ProjectResponseDto> getProjectPage(Specification<ProjectEntity> specification, Pageable pageable);

    ProjectResponseDto saveProject(String name, ProjectStatus projectStatus);

    void updateProject(String name, ProjectStatus status, UUID id);

    ProjectResponseDto getProjectById(UUID id);
}
