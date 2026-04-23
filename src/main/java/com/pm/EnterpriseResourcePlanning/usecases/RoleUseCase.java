package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.datasource.impl.RolePermissionDataSourceImpl;
import com.pm.EnterpriseResourcePlanning.datasource.impl.RolesDataSourceImpl;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.RoleRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.PermissionResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.RoleResponseDto;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.exceptions.AlreadyExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleUseCase {

    private final RolesDataSourceImpl dataSource;
    private final RolePermissionDataSourceImpl rolePermissionDataSource;

    public RoleResponseDto createRole(@Valid RoleRequestDto requestDto) {
        return dataSource.saveRole(requestDto.name(), requestDto.status());
    }


    public Page<RoleResponseDto> getRolePages() {
        return dataSource.getRolePages(PageRequest.of(0, 25));
    }

    public RoleResponseDto getRoleById(UUID id) {
        return dataSource.getRoleById(id);
    }

    public void deactivateRole(UUID id) {
        dataSource.deactivateRole(id);
    }

    public void connectRolePermission(@Valid IntermediateRequestDto rolePermissionRequestDto) {

        if (rolePermissionExists(rolePermissionRequestDto)) {
            throw new AlreadyExistsException(ErrorMessages.ROLE_PERMISSION_ALREADY_EXISTS, rolePermissionRequestDto.uuid(), rolePermissionRequestDto.uuid1());
        }

        rolePermissionDataSource.saveRolePermissions(rolePermissionRequestDto.uuid(), rolePermissionRequestDto.uuid1());
    }

    public boolean rolePermissionExists(@Valid IntermediateRequestDto rolePermissionRequestDto) {
        return rolePermissionDataSource.exists(rolePermissionRequestDto.uuid(), rolePermissionRequestDto.uuid1());
    }

    public void deleteRolePermissionLink(@Valid IntermediateRequestDto rolePermissionRequestDto) {
        rolePermissionDataSource.removeUserRoleLink(rolePermissionRequestDto.uuid(), rolePermissionRequestDto.uuid1());
    }

    public List<PermissionResponseDto> getRolePermissions(UUID id) {
        return rolePermissionDataSource.getRolePermissions(id);
    }
}
