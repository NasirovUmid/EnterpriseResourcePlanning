package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.datasource.RoleDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.RolePermissionDataSource;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.RoleRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.PermissionResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.RoleResponseDto;
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

    @Transactional
    public RoleResponseDto createRole(@Valid RoleRequestDto requestDto) {
        return dataSource.saveRole(requestDto.name(), requestDto.status());
    }

    @Transactional(readOnly = true)
    public List<RoleResponseDto> getRolePages() {
        return dataSource.getRolePages();
    }

    @Transactional
    public void deactivateRole(UUID id) {
        dataSource.deactivateRole(id);
    }

    @Transactional
    public void connectRolePermission(@Valid IntermediateRequestDto rolePermissionRequestDto) {

        if (rolePermissionExists(rolePermissionRequestDto)) {
            throw new AlreadyExistsException(ErrorMessages.ROLE_PERMISSION_ALREADY_EXISTS, rolePermissionRequestDto.uuid(), rolePermissionRequestDto.uuid1());
        }

        rolePermissionDataSource.saveRolePermissions(rolePermissionRequestDto.uuid(), rolePermissionRequestDto.uuid1());
    }

    public boolean rolePermissionExists(@Valid IntermediateRequestDto rolePermissionRequestDto) {
        return rolePermissionDataSource.exists(rolePermissionRequestDto.uuid(), rolePermissionRequestDto.uuid1());
    }

    @Transactional
    public void deleteRolePermissionLink(@Valid IntermediateRequestDto rolePermissionRequestDto) {
        rolePermissionDataSource.removeUserRoleLink(rolePermissionRequestDto.uuid(), rolePermissionRequestDto.uuid1());
    }

    @Transactional(readOnly = true)
    public List<PermissionResponseDto> getRolePermissions(UUID id) {
        return rolePermissionDataSource.getRolePermissions(id);
    }
}
