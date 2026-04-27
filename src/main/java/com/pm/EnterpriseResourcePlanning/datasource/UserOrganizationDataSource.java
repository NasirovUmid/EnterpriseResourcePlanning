package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.OrganizationResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.UserResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.OrganizationEntity;

import java.util.List;
import java.util.UUID;

public interface UserOrganizationDataSource {

    void saveUserOrganization(UUID userId, UUID organizationId);

    boolean exists(UUID userId, UUID organizationId);

    void removeUserOrganizationLink(UUID userId, UUID organizationId);

    List<UserResponseDto> getOrganizationUsers(UUID organizationId);

    List<OrganizationResponseDto> getUserOrganizations(UUID userId);

}
