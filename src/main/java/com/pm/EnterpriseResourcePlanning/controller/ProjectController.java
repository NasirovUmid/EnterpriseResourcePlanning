package com.pm.EnterpriseResourcePlanning.controller;

import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ProjectRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ProjectUpdateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.OrganizationResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProjectResponseDto;
import com.pm.EnterpriseResourcePlanning.enums.ProjectStatus;
import com.pm.EnterpriseResourcePlanning.usecases.ProjectUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectUseCase projectUseCase;

    @PostMapping
    public ResponseEntity<ProjectResponseDto> createProject(@Valid @RequestBody ProjectRequestDto projectRequestDto) {

        ProjectResponseDto projectResponseDto = projectUseCase.createProject(projectRequestDto);

        return ResponseEntity.status(203).body(projectResponseDto);
    }

    @PostMapping("/organizations")
    public ResponseEntity<Void> createProjectOrganizationLink(@Valid @RequestBody IntermediateRequestDto projectOrganizationDto) {

        projectUseCase.linkProjectOrganization(projectOrganizationDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Page<ProjectResponseDto> getProjectsPage(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "AWAITING") ProjectStatus status,
            @RequestParam(required = false, defaultValue = "name,asc") String sort) {

        return projectUseCase.getProjectsPage(page, size, name, status, sort);
    }

    @GetMapping("/{id}")
    public ProjectResponseDto getProjectById(@PathVariable(name = "id") UUID id) {

        return projectUseCase.getProjectById(id);
    }

    @GetMapping("/organizations-exist")
    public ResponseEntity<Void> projectOrganizationExists(@Valid @RequestBody IntermediateRequestDto projectRequestDto) {

        boolean doesExists = projectUseCase.projectOrganizationExists(projectRequestDto);

        return doesExists ? ResponseEntity.ok().build() : ResponseEntity.status(404).build();
    }

    @GetMapping("/organizations/{id}")
    public List<OrganizationResponseDto> getProjectsOrganizations(@PathVariable(name = "id") UUID id) {
        return projectUseCase.getProjectsOrganizations(id);
    }

    @GetMapping("/organizations-projects/{id}")
    public List<ProjectResponseDto> getOrganizationProjects(@PathVariable(name = "id") UUID id) {
        return projectUseCase.getOrganizationProjects(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProject(@PathVariable(name = "id") UUID id, @RequestBody ProjectUpdateRequestDto projectUpdateRequestDto) {

        projectUseCase.updateProject(id, projectUpdateRequestDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/organizations")
    public ResponseEntity<Void> deleteProjectOrganizationLink(@Valid @RequestBody IntermediateRequestDto projectOrganizationRequestDto) {

        projectUseCase.deleteProjectOrganizationLink(projectOrganizationRequestDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/user")
    public ResponseEntity<Void> saveUserProject(@Valid @RequestBody IntermediateRequestDto requestDto) {

        projectUseCase.saveUserProject(requestDto);

        return ResponseEntity.status(201).build();
    }

    @PostMapping("/projects-user-exists")
    public ResponseEntity<Void> existUserProject(@Valid @RequestBody IntermediateRequestDto requestDto) {

        boolean exists = projectUseCase.exists(requestDto);

        return exists ? ResponseEntity.ok().build() : ResponseEntity.status(404).build();
    }

    @DeleteMapping("/project-user")
    public ResponseEntity<Void> deleteUserProject(@Valid @RequestBody IntermediateRequestDto requestDto) {

        projectUseCase.removeUserProject(requestDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/users-projects/{id}")
    public List<ProjectResponseDto> getUserProjects(@PathVariable(name = "id")UUID id){

        return projectUseCase.getUserProjects(id);
    }
}
