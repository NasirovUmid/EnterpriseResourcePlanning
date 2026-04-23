package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.ProjectEntity;
import com.pm.EnterpriseResourcePlanning.entity.UserEntity;
import com.pm.EnterpriseResourcePlanning.enums.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface ProjectDao {

    List<UserEntity> findUsersByProject(UUID projectId);

    Page<ProjectEntity> getProjectPage(Specification<ProjectEntity> specification, Pageable pageable);

    ProjectEntity saveProject(String name, ProjectStatus projectStatus);

    void updateProject(String name, ProjectStatus status, UUID id);

    ProjectEntity getProjectById(UUID id);


}
