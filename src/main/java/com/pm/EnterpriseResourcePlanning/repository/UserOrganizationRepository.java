package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserOrganizationRepository {

    void saveUserOrganization(UUID userId, UUID organizationId);

    boolean exists(UUID userId, UUID organizationId);

    void removeUserOrganizationLink(UUID userId, UUID organizationId);

    List<UserEntity> getOrganizationUsers(UUID organizationId);

}
