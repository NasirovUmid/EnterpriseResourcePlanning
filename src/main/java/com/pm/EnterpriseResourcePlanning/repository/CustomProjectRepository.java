package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.ProjectEntity;

import java.util.Optional;

public interface CustomProjectRepository {

    Optional<ProjectEntity> save(String name);

}
