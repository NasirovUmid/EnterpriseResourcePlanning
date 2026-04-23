package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.ModuleDao;
import com.pm.EnterpriseResourcePlanning.entity.ModuleEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.exceptions.NotFoundException;
import com.pm.EnterpriseResourcePlanning.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ModuleDaoImpl implements ModuleDao {

    private final ModuleRepository repository;

    @Override
    public ModuleEntity saveModule(String name) {
        return repository.saveModule(name).orElseThrow(RuntimeException::new);
    }

    @Override
    public Page<ModuleEntity> getModuleEntities(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void updateModule(String name, UUID id) {
         repository.updateModule(name, id);
    }

    @Override
    public ModuleEntity getModuleById(UUID id) {
        return repository.getModuleById(id).orElseThrow(() -> new NotFoundException(ErrorMessages.MODULE_NOT_FOUND,id));
    }
}
