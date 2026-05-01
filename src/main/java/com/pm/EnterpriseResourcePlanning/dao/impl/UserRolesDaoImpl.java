package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.UserRolesDao;
import com.pm.EnterpriseResourcePlanning.entity.RolesEntity;
import com.pm.EnterpriseResourcePlanning.repository.impl.UserRoleRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserRolesDaoImpl implements UserRolesDao {

    private final UserRoleRepositoryImpl userRoleRepository;

    @Override
    public void saveUserRole(UUID userId, UUID roleId) {
        userRoleRepository.saveUserRole(userId, roleId);
    }

    @Override
    public boolean exists(UUID userId, UUID roleId) {
        return userRoleRepository.exists(userId, roleId);
    }

    @Override
    public void removeUserRoleLink(UUID userId, UUID roleId) {
        userRoleRepository.removeUserRoleLink(userId, roleId);
    }

    @Override
    public List<RolesEntity> findRolesByUserId(UUID userId) {
        return userRoleRepository.findRolesByUserId(userId);
    }

    @Override
    public List<String> findAllAuthoritiesByUserId(UUID userId) {
        return userRoleRepository.findAllAuthoritiesByUserId(userId);
    }
}
