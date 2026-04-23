package com.pm.EnterpriseResourcePlanning.controller;

import com.pm.EnterpriseResourcePlanning.dto.requestdtos.PermissionRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.PermissionUpdateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.PermissionResponseDto;
import com.pm.EnterpriseResourcePlanning.usecases.PermissionUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionUseCase permissionUseCase;

    @PostMapping
    public ResponseEntity<PermissionResponseDto> createPermission(@Valid @RequestBody PermissionRequestDto permissionRequestDto) {

        PermissionResponseDto permissionResponseDto = permissionUseCase.createPermission(permissionRequestDto);

        return ResponseEntity.status(201).body(permissionResponseDto);
    }

    @GetMapping
    public Page<PermissionResponseDto> getPermissionPages(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "name,asc") String sort,
            @RequestParam(required = false, defaultValue = "name") String name
    ) {

        return permissionUseCase.getPermissionPages(page, size, sort, name);
    }

    @GetMapping("/{id}")
    public PermissionResponseDto getPermissionById(@PathVariable(name = "id") UUID id) {
        return permissionUseCase.getPermissionById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePermission(@PathVariable(name = "id") UUID id, @Valid @RequestBody PermissionUpdateRequestDto updateRequestDto) {

        permissionUseCase.updatePermission(id, updateRequestDto);

        return ResponseEntity.ok().build();
    }
}
