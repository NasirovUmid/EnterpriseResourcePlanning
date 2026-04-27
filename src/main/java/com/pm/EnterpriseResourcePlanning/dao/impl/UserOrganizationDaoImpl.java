package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.UserOrganizationDao;
import com.pm.EnterpriseResourcePlanning.entity.OrganizationEntity;
import com.pm.EnterpriseResourcePlanning.entity.UserEntity;
import com.pm.EnterpriseResourcePlanning.repository.impl.UserOrganizationRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserOrganizationDaoImpl implements UserOrganizationDao {

    private final UserOrganizationRepositoryImpl repository;

    @Override
    public void saveUserOrganization(UUID userId, UUID organizationId) {
        repository.saveUserOrganization(userId, organizationId);
    }

    @Override
    public boolean exists(UUID userId, UUID organizationId) {
        return repository.exists(userId, organizationId);
    }

    @Override
    public void removeUserOrganizationLink(UUID userId, UUID organizationId) {
        repository.removeUserOrganizationLink(userId, organizationId);
    }

    @Override
    public List<UserEntity> getOrganizationUsers(UUID organizationId) {
        return repository.getOrganizationUsers(organizationId);
    }

    @Override
    public List<OrganizationEntity> getUserOrganizations(UUID userId) {
        return repository.getUserOrganizations(userId);
    }
}
