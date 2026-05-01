package com.pm.EnterpriseResourcePlanning.controller;

import com.pm.EnterpriseResourcePlanning.dto.filters.UserFilterDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.UserRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.UserUpdateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.RoleResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.UserResponseDto;
import com.pm.EnterpriseResourcePlanning.usecases.UserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase userUseCase;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponseDto> createUser(@Valid
                                                      @RequestPart("data") UserRequestDto userRequestDto,
                                                      @RequestPart("avatar") MultipartFile file) throws IOException {

        UserResponseDto userResponseDto = userUseCase.createUser(userRequestDto, file);

        return ResponseEntity.status(201).body(userResponseDto);

    }

    @PostMapping("/roles")
    public ResponseEntity<Void> connectUserToRole(@Valid @RequestBody IntermediateRequestDto userRoleRequestDto) {

        userUseCase.connectUserToRole(userRoleRequestDto);

        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}/roles")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR', 'MOD_PROJECTS', 'MOD_ORGANIZATIONS', 'MOD_USERS', 'MOD_PRODUCTS', 'MOD_CONTRACTS') or #id == authentication.principal.id")
    public List<RoleResponseDto> getUserRoles(@PathVariable(name = "id") UUID id) {
        return userUseCase.getUserRoles(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MOD_USERS')")
    public Page<UserResponseDto> getUsersPage(@RequestParam(defaultValue = "0", name = "page") int page,
                                              @RequestParam(defaultValue = "20", name = "size") int size,
                                              @RequestParam(defaultValue = "fullName,asc", name = "sort") String sort,
                                              @Valid @ModelAttribute UserFilterDto userFilterDto
    ) {
        return userUseCase.getUsersPage(page, size, userFilterDto, sort);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MOD_USERS') or #id == authentication.principal.id")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UserUpdateRequestDto userRequestDto, @PathVariable(name = "id") UUID id) {

        userUseCase.updateUser(userRequestDto, id);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR', 'MOD_USERS') or #id == authentication.principal.id")
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable(name = "id") UUID id) {

        UserResponseDto responseDto = userUseCase.getUserById(id);
        log.info("{}", responseDto);

        return responseDto;

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deactivateUser(@PathVariable(name = "id") UUID id) {

        userUseCase.deactivateUser(id);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MOD_USERS')")
    @DeleteMapping("/link")
    public ResponseEntity<Void> deleteUserRoleLink(@Valid @RequestBody IntermediateRequestDto userRoleRequestDto) {

        userUseCase.deleteUserRoleLink(userRoleRequestDto);

        return ResponseEntity.ok().build();
    }
}
