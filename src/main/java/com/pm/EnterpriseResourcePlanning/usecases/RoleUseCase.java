package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.datasource.RoleDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.RolePermissionDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.UserRoleDataSource;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
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
    public void connectRolePermission(@Valid IntermediateRequestDto rolePermissionRequestDto) {

        if (rolePermissionExists(rolePermissionRequestDto)) {
            throw new AlreadyExistsException(ErrorMessages.ROLE_PERMISSION_ALREADY_EXISTS, rolePermissionRequestDto.uuid(), rolePermissionRequestDto.uuid1());
        }

        rolePermissionDataSource.saveRolePermissions(rolePermissionRequestDto.uuid(), rolePermissionRequestDto.uuid1());
    }

    public boolean rolePermissionExists(@Valid IntermediateRequestDto rolePermissionRequestDto) {
        return rolePermissionDataSource.exists(rolePermissionRequestDto.uuid(), rolePermissionRequestDto.uuid1());
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRolePermissionLink(@Valid IntermediateRequestDto rolePermissionRequestDto) {
        rolePermissionDataSource.removeUserRoleLink(rolePermissionRequestDto.uuid(), rolePermissionRequestDto.uuid1());
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
