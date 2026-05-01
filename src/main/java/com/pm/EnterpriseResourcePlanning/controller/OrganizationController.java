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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ADMIN','MOD_ORGANIZATIONS')")
    public ResponseEntity<Void> createUserOrganizationLink(@Valid @RequestBody IntermediateRequestDto userOrganizationRequestDto) {

        organizationUseCase.createUserOrganizationLink(userOrganizationRequestDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MOD_ORGANIZATIONS')")
    public Page<OrganizationResponseDto> getOrganizationsPage(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "name,asc") String sort,
            @Valid @ModelAttribute OrganizationFilterDto organizationFilterDto) {

        return organizationUseCase.getOrganizationsPage(page, size, sort, organizationFilterDto);
    }

    @GetMapping("/{id}")
    public OrganizationResponseDto getOrganizationById(@PathVariable(name = "id") UUID id) {

        return organizationUseCase.getOrganizationById(id);
    }

    @GetMapping("/users/{id}")
    public List<UserResponseDto> getOrganizationUsers(@PathVariable(name = "id") UUID id) {
        return organizationUseCase.getOrganizationUsers(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MOD_ORGANIZATIONS')")
    @DeleteMapping("/user")
    public ResponseEntity<Void> removeUserOrganizationLink(@Valid @RequestBody IntermediateRequestDto userOrganizationRequestDto) {

        organizationUseCase.removeUserOrganizationLink(userOrganizationRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOrganization(@PathVariable(name = "id") UUID id, @RequestBody OrganizationUpdateRequestDto organizationUpdateRequestDto) {

        organizationUseCase.updateOrganization(id, organizationUpdateRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user-organization/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR') or #userId == authentication.principal.id")
    public List<OrganizationResponseDto> getUserOrganization(@PathVariable(name = "id") UUID userId) {

        return organizationUseCase.getUserOrganizations(userId);
    }

}
