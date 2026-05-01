package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.ModuleEntity;

import java.util.Optional;

public interface CustomModuleRepository {

    Optional<ModuleEntity> saveModule(String name);

}
