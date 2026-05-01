package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.RoleDao;
import com.pm.EnterpriseResourcePlanning.entity.RolesEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.enums.RoleStatus;
import com.pm.EnterpriseResourcePlanning.exceptions.NotFoundException;
import com.pm.EnterpriseResourcePlanning.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoleDaoImpl implements RoleDao {

    private final RolesRepository repository;

    @Override
    public RolesEntity saveRole(String name, RoleStatus status) {
        return repository.saveRole(name, status).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<RolesEntity> getRolePages() {
        return repository.findAll();
    }

    @Override
    public void deactivateRole(UUID id) {
        repository.deactivateRole(id);
    }

    @Override
    public RolesEntity getRoleById(UUID id) {
        return repository.findRolesEntityById(id).orElseThrow(() -> new NotFoundException(ErrorMessages.ROLE_NOT_FOUND, id));
    }
}
