package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.PermissionDao;
import com.pm.EnterpriseResourcePlanning.entity.ActionEntity;
import com.pm.EnterpriseResourcePlanning.entity.ModuleEntity;
import com.pm.EnterpriseResourcePlanning.entity.PermissionEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.exceptions.NotFoundException;
import com.pm.EnterpriseResourcePlanning.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PermissionDaoImpl implements PermissionDao {

    private final PermissionRepository repository;

    @Override
    public List<String> findAllPermissionNamesByUserId(UUID userId) {
        return repository.findAllPermissionNamesByUserId(userId);
    }

    @Override
    public PermissionEntity savePermission(String name, UUID module, UUID action) {
        return repository.savePermission(name, module, action).orElseThrow(RuntimeException::new);
    }

    @Override
    public Page<PermissionEntity> getPermissionPage(Specification<PermissionEntity> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    @Override
    public void updatePermission(String name, UUID module, UUID action, UUID id) {
        repository.updatePermission(name, module, action, id);
    }

    @Override
    public PermissionEntity getPermissionById(UUID id) {
        return repository.findPermissionEntityById(id).orElseThrow(() -> new NotFoundException(ErrorMessages.PERMISSION_NOT_FOUND, id));
    }
}
