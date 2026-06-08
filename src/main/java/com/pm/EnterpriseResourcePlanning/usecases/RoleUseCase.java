package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.datasource.RoleDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.RolePermissionDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.UserRoleDataSource;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.LinkRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.RoleRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.PermissionResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.RoleResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.UserResponseDto;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.exceptions.AlreadyExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleUseCase {

    private final RoleDataSource dataSource;
    private final RolePermissionDataSource rolePermissionDataSource;
    private final UserRoleDataSource userRoleDataSource;

    @Transactional
    public RoleResponseDto createRole(@Valid RoleRequestDto requestDto) {
        return dataSource.saveRole(requestDto.name(), requestDto.status());
    }

    @Transactional(readOnly = true)
    public List<RoleResponseDto> getRolePages() {
        return dataSource.getRolePages();
    }

    @Transactional(readOnly = true)
    public RoleResponseDto getRoleById(UUID id) {
        return dataSource.getRoleByIdDto(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deactivateRole(UUID id) {
        dataSource.deactivateRole(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateRole(UUID id, @Valid RoleRequestDto requestDto) {
        dataSource.updateRole(id, requestDto.name(), requestDto.status());
    }

    @Transactional(rollbackFor = Exception.class)
    public void connectRolePermission(@Valid LinkRequestDto rolePermissionRequestDto) {

        UUID roleId = rolePermissionRequestDto.entityId();
        UUID permissionId = rolePermissionRequestDto.relatedEntityId();

        if (rolePermissionExists(rolePermissionRequestDto)) {
            throw new AlreadyExistsException(ErrorMessages.ROLE_PERMISSION_ALREADY_EXISTS, roleId, permissionId);
        }

        rolePermissionDataSource.saveRolePermissions(roleId, permissionId);
    }

    public boolean rolePermissionExists(@Valid LinkRequestDto rolePermissionRequestDto) {

        UUID roleId = rolePermissionRequestDto.entityId();
        UUID permissionId = rolePermissionRequestDto.relatedEntityId();

        return rolePermissionDataSource.exists(roleId, permissionId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRolePermissionLink(@Valid LinkRequestDto rolePermissionRequestDto) {

        UUID roleId = rolePermissionRequestDto.entityId();
        UUID permissionId = rolePermissionRequestDto.relatedEntityId();

        rolePermissionDataSource.removeUserRoleLink(roleId, permissionId);
    }

    @Transactional(readOnly = true)
    public List<PermissionResponseDto> getRolePermissions(UUID id) {
        return rolePermissionDataSource.getRolePermissions(id);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getRoleUsers(UUID id) {
        return userRoleDataSource.findUsersByRoleId(id);
    }
}
