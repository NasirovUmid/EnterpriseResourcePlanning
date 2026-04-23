package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.ModuleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ModuleDao {

    ModuleEntity saveModule(String name);

    Page<ModuleEntity> getModuleEntities(Pageable pageable);

    void updateModule(String name, UUID id);

    ModuleEntity getModuleById(UUID id);
}
