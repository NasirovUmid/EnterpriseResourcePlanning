package com.pm.EnterpriseResourcePlanning.controller;

import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.RoleRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.PermissionResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.RoleResponseDto;
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
    public ResponseEntity<RoleResponseDto> createRole(@Valid @RequestBody RoleRequestDto requestDto) {

        RoleResponseDto roleResponseDto = roleUseCase.createRole(requestDto);

        return ResponseEntity.status(201).body(roleResponseDto);
    }

    @PostMapping("/permissions")
    public ResponseEntity<Void> connectRolePermission(@Valid @RequestBody IntermediateRequestDto rolePermissionRequestDto) {
        roleUseCase.connectRolePermission(rolePermissionRequestDto);

        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public List<RoleResponseDto> getRoles() {
        return roleUseCase.getRolePages();
    }

    @GetMapping("/permissions/{id}")
    public List<PermissionResponseDto> getRolePermissions(@PathVariable(name = "id") UUID id) {

        return roleUseCase.getRolePermissions(id);
    }


    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<Void> deleteRolePermissionLink(@Valid @RequestBody IntermediateRequestDto rolePermissionRequestDto) {

        roleUseCase.deleteRolePermissionLink(rolePermissionRequestDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateRole(@PathVariable(name = "id") UUID id) {

        roleUseCase.deactivateRole(id);

        return ResponseEntity.ok().build();
    }
}
