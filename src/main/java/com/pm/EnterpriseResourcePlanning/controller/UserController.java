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
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/roles")
    public ResponseEntity<Boolean> userRoleExists(@Valid @RequestBody IntermediateRequestDto userRoleRequestDto) {

        Boolean doesExist = userUseCase.userRoleExists(userRoleRequestDto);

        return doesExist ? ResponseEntity.ok().build() : ResponseEntity.status(404).build();
    }

    @GetMapping("/{id}/roles")
    public List<RoleResponseDto> getUserRoles(@PathVariable(name = "id") UUID id) {
        return userUseCase.getUserRoles(id);
    }

    @GetMapping
    public Page<UserResponseDto> getUsersPage(@RequestParam(defaultValue = "0", name = "page") int page,
                                              @RequestParam(defaultValue = "20", name = "size") int size,
                                              @RequestParam(defaultValue = "fullName,asc", name = "sort") String sort,
                                              @Valid @RequestBody UserFilterDto userFilterDto
    ) {
        return userUseCase.getUsersPage(page, size, userFilterDto, sort);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UserUpdateRequestDto userRequestDto, @PathVariable(name = "id") UUID id) {

        userUseCase.updateUser(userRequestDto, id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable(name = "id") UUID id) {

        return userUseCase.getUserById(id);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateUser(@PathVariable(name = "id") UUID id) {

        userUseCase.deactivateUser(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/link")
    public ResponseEntity<Void> deleteUserRoleLink(@Valid @RequestBody IntermediateRequestDto userRoleRequestDto) {

        userUseCase.deleteUserRoleLink(userRoleRequestDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> checkUserAccess(@PathVariable(name = "id") UUID id, @RequestBody String moduleName, String actionName) {

        boolean hasAccess = userUseCase.checkUser(id, moduleName, actionName);

        return hasAccess ? ResponseEntity.ok().build() : ResponseEntity.status(403).build();
    }
}
