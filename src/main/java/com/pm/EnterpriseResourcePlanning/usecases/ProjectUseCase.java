package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.datasource.impl.ProjectDataSourceImpl;
import com.pm.EnterpriseResourcePlanning.datasource.impl.ProjectOrganizationDataSourceImpl;
import com.pm.EnterpriseResourcePlanning.datasource.impl.UserProjectDataSourceImpl;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ProjectRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ProjectUpdateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.OrganizationResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProjectResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ProjectEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.enums.ProjectStatus;
import com.pm.EnterpriseResourcePlanning.exceptions.AlreadyExistsException;
import com.pm.EnterpriseResourcePlanning.exceptions.IllegalStateException;
import com.pm.EnterpriseResourcePlanning.specifications.ProjectSpecifications;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectUseCase {

    private final ProjectDataSourceImpl projectDataSource;
    private final ProjectOrganizationDataSourceImpl projectOrganizationDataSource;
    private final UserProjectDataSourceImpl userProjectDataSource;

    public ProjectResponseDto createProject(ProjectRequestDto projectRequestDto) {

        return projectDataSource.saveProject(projectRequestDto.name(), projectRequestDto.status());
    }

    public Page<ProjectResponseDto> getProjectsPage(int page, int size, String name, ProjectStatus status, String sort) {

        Specification<ProjectEntity> specification = ProjectSpecifications.build(name, status);

        Pageable pageable = PageRequest.of(page, size, toProjectEntitySort(sort));

        return projectDataSource.getProjectPage(specification, pageable);

    }

    public ProjectResponseDto getProjectById(UUID id) {
        return projectDataSource.getProjectById(id);
    }

    public void updateProject(UUID id, ProjectUpdateRequestDto projectUpdateRequestDto) {

        if (projectUpdateRequestDto == null || (projectUpdateRequestDto.name() == null && projectUpdateRequestDto.status() == null)) {
            throw new RuntimeException();
        }

        projectDataSource.updateProject(projectUpdateRequestDto.name(), projectUpdateRequestDto.status(), id);

    }


    public void linkProjectOrganization(@Valid IntermediateRequestDto projectOrganizationDto) {

        if (!projectDataSource.getProjectById(projectOrganizationDto.uuid()).status().equals(ProjectStatus.AWAITING) ||
                !projectDataSource.getProjectById(projectOrganizationDto.uuid()).status().equals(ProjectStatus.ACTIVE)) {
            throw new IllegalStateException(ErrorMessages.PROJECT_IS_NOT_ACCESSIBLE, projectOrganizationDto.uuid(), projectOrganizationDto.uuid1());
        }

        if (projectOrganizationExists(projectOrganizationDto)) {
            throw new AlreadyExistsException(ErrorMessages.PROJECT_ORGANIZATION_ALREADY_EXISTS, projectOrganizationDto.uuid(), projectOrganizationDto.uuid1());
        }

        projectOrganizationDataSource.linkProjectOrganization(projectOrganizationDto.uuid(), projectOrganizationDto.uuid1());
    }

    public boolean projectOrganizationExists(@Valid IntermediateRequestDto projectRequestDto) {
        return projectOrganizationDataSource.exists(projectRequestDto.uuid(), projectRequestDto.uuid1());
    }

    public void deleteProjectOrganizationLink(@Valid IntermediateRequestDto projectOrganizationRequestDto) {
        projectOrganizationDataSource.removeProjectOrganizationLink(projectOrganizationRequestDto.uuid(), projectOrganizationRequestDto.uuid1());
    }

    public List<OrganizationResponseDto> getProjectsOrganizations(UUID id) {
        return projectOrganizationDataSource.getProjectOrganizations(id);
    }

    public List<ProjectResponseDto> getOrganizationProjects(UUID id) {
        return projectOrganizationDataSource.getOrganizationProjects(id);
    }

    private Sort toProjectEntitySort(String sort) {
        if (sort == null || !sort.contains(",")) {
            return Sort.by("fullName").ascending(); // Сортировка по умолчанию
        }

        String[] parts = sort.split(",");
        String field = parts[0]; // поле: "fullName", "price", "unit"
        String direction = parts[1]; // направление: "asc" или "desc"

        return direction.equalsIgnoreCase("desc")
                ? Sort.by(field).descending()
                : Sort.by(field).ascending();
    }

    public void saveUserProject(@Valid IntermediateRequestDto requestDto) {
        userProjectDataSource.saveUserProject(requestDto.uuid(), requestDto.uuid1());
    }

    public boolean exists(IntermediateRequestDto requestDto) {
        return userProjectDataSource.existsUserProject(requestDto.uuid(), requestDto.uuid1());
    }

    public void removeUserProject(@Valid IntermediateRequestDto requestDto) {
        userProjectDataSource.removeUserProject(requestDto.uuid(), requestDto.uuid1());
    }

    public List<ProjectResponseDto> getUserProjects(UUID id) {
        return userProjectDataSource.getUserProjects(id);
    }
}

