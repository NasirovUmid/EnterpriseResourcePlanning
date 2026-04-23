package com.pm.EnterpriseResourcePlanning.controller;

import com.pm.EnterpriseResourcePlanning.dto.filters.OrganizationFilterDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.OrganizationRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.OrganizationUpdateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.OrganizationResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.UserResponseDto;
import com.pm.EnterpriseResourcePlanning.usecases.OrganizationUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationUseCase organizationUseCase;

    @PostMapping
    public ResponseEntity<OrganizationResponseDto> createOrganization(@Valid @RequestBody OrganizationRequestDto organizationRequestDto) {

        OrganizationResponseDto organizationResponseDto = organizationUseCase.createOrganization(organizationRequestDto);

        return ResponseEntity.status(201).body(organizationResponseDto);
    }

    @PostMapping("/users")
    public ResponseEntity<Void> createUserOrganizationLink(@Valid @RequestBody IntermediateRequestDto userOrganizationRequestDto) {

        organizationUseCase.createUserOrganizationLink(userOrganizationRequestDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Page<OrganizationResponseDto> getOrganizationsPage(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "name,asc") String sort,
            @RequestBody OrganizationFilterDto organizationFilterDto) {

        return organizationUseCase.getOrganizationsPage(page, size, sort, organizationFilterDto);
    }

    @GetMapping("/{id}")
    public OrganizationResponseDto getOrganizationById(@PathVariable(name = "id") UUID id) {

        return organizationUseCase.getOrganizationById(id);
    }

    @PostMapping("/users-exists")
    public ResponseEntity<Void> existsUserOrganization(@Valid @RequestBody IntermediateRequestDto userOrganizationRequestDto) {

        boolean doesExists = organizationUseCase.exists(userOrganizationRequestDto);

        return doesExists ? ResponseEntity.ok().build() : ResponseEntity.status(404).build();
    }

    @GetMapping("/users/{id}")
    public List<UserResponseDto> getOrganizationUsers(@PathVariable(name = "id")UUID id){
        return organizationUseCase.getOrganizationUsers(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUserOrganizationLink(@Valid @RequestBody IntermediateRequestDto userOrganizationRequestDto){

        organizationUseCase.removeUserOrganizationLink(userOrganizationRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOrganization(@PathVariable(name = "id") UUID id, OrganizationUpdateRequestDto organizationUpdateRequestDto) {

        organizationUseCase.updateOrganization(id, organizationUpdateRequestDto);
        return ResponseEntity.ok().build();
    }


}
