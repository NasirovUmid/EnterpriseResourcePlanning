package com.pm.EnterpriseResourcePlanning.controller;

import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.RoleRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.PermissionResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.RoleResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.UserResponseDto;
import com.pm.EnterpriseResourcePlanning.usecases.RoleUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleUseCase roleUseCase;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOD_ROLES')")
    public ResponseEntity<RoleResponseDto> createRole(@Valid @RequestBody RoleRequestDto requestDto) {

        RoleResponseDto roleResponseDto = roleUseCase.createRole(requestDto);

        return ResponseEntity.status(201).body(roleResponseDto);
    }

    @PostMapping("/permissions")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOD_ROLES')")
    public ResponseEntity<Void> connectRolePermission(@Valid @RequestBody IntermediateRequestDto rolePermissionRequestDto) {
        roleUseCase.connectRolePermission(rolePermissionRequestDto);

        return ResponseEntity.status(201).build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOD_ROLES', 'AUDITOR')")
    public List<RoleResponseDto> getRoles() {
        return roleUseCase.getRolePages();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOD_ROLES', 'AUDITOR')")
    public RoleResponseDto getRoleById(@PathVariable(name = "id") UUID id) {
        return roleUseCase.getRoleById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOD_ROLES')")
    public ResponseEntity<Void> updateRole(@PathVariable(name = "id") UUID id, @Valid @RequestBody RoleRequestDto requestDto) {
        roleUseCase.updateRole(id, requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/permissions/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOD_ROLES', 'AUDITOR')")
    public List<PermissionResponseDto> getRolePermissions(@PathVariable(name = "id") UUID id) {

        return roleUseCase.getRolePermissions(id);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOD_ROLES', 'AUDITOR')")
    public List<UserResponseDto> getRoleUsers(@PathVariable(name = "id") UUID id) {
        return roleUseCase.getRoleUsers(id);
    }

    @DeleteMapping("/permissions/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOD_ROLES')")
    public ResponseEntity<Void> deleteRolePermissionLink(@Valid @RequestBody IntermediateRequestDto rolePermissionRequestDto) {

        roleUseCase.deleteRolePermissionLink(rolePermissionRequestDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MOD_ROLES')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateRole(@PathVariable(name = "id") UUID id) {

        roleUseCase.deactivateRole(id);

        return ResponseEntity.ok().build();
    }
}
