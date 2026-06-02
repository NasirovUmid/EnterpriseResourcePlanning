package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.RoleResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.UserResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserRoleDataSource {

    void saveUserRole(UUID userId, UUID roleId);

    boolean exists(UUID userId, UUID roleId);

    void removeUserRoleLink(UUID userId, UUID roleId);

    List<RoleResponseDto> findRolesByUserId(UUID userId);

    List<UserResponseDto> findUsersByRoleId(UUID roleId);

    List<String> findAllAuthoritiesByUserId(UUID userId);


}
