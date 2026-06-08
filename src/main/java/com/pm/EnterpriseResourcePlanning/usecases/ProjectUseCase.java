package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.datasource.ProjectDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.ProjectOrganizationDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.UserProjectDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.SortResolver;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.LinkRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ProjectRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ProjectUpdateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.OrganizationResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProjectResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ProjectEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.enums.ProjectStatus;
import com.pm.EnterpriseResourcePlanning.enums.SortType;
import com.pm.EnterpriseResourcePlanning.exceptions.AlreadyExistsException;
import com.pm.EnterpriseResourcePlanning.exceptions.IllegalStateException;
import com.pm.EnterpriseResourcePlanning.specifications.ProjectSpecifications;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectUseCase {

    private final ProjectDataSource projectDataSource;
    private final ProjectOrganizationDataSource projectOrganizationDataSource;
    private final UserProjectDataSource userProjectDataSource;

    @Transactional(rollbackFor = Exception.class)
    public ProjectResponseDto createProject(ProjectRequestDto projectRequestDto) {

        return projectDataSource.saveProject(projectRequestDto.name());
    }

    @Transactional(readOnly = true)
    public Page<ProjectResponseDto> getProjectsPage(int page, int size, String name, ProjectStatus status, String sort) {


        Specification<ProjectEntity> specification = ProjectSpecifications.build(name, status);

        Sort sort1 = SortResolver.resolver(SortType.PROJECT, sort);

        Pageable pageable = PageRequest.of(page, size, sort1);

        return projectDataSource.getProjectPage(specification, pageable);

    }

    @Transactional(readOnly = true)
    public ProjectResponseDto getProjectById(UUID id) {
        return projectDataSource.getProjectById(id);
    }

    public void updateProject(UUID id, ProjectUpdateRequestDto projectUpdateRequestDto) {

        if (projectUpdateRequestDto == null || (projectUpdateRequestDto.name() == null && projectUpdateRequestDto.status() == null)) {
            throw new RuntimeException();
        }

        projectDataSource.updateProject(projectUpdateRequestDto.name(), projectUpdateRequestDto.status(), id);

    }

    @Transactional(rollbackFor = Exception.class)
    public void linkProjectOrganization(@Valid LinkRequestDto projectOrganizationDto) {

        UUID projectId = projectOrganizationDto.entityId();
        UUID organizationId = projectOrganizationDto.relatedEntityId();

        if (!projectDataSource.getProjectById(projectId).status().equals(ProjectStatus.AWAITING) &&
                !projectDataSource.getProjectById(projectId).status().equals(ProjectStatus.ACTIVE)) {
            throw new IllegalStateException(ErrorMessages.PROJECT_IS_NOT_ACCESSIBLE, projectId, organizationId);
        }

        if (projectOrganizationExists(projectOrganizationDto)) {
            throw new AlreadyExistsException(ErrorMessages.PROJECT_ORGANIZATION_ALREADY_EXISTS, projectId, organizationId);
        }

        projectOrganizationDataSource.linkProjectOrganization(projectId, organizationId);
    }

    public boolean projectOrganizationExists(@Valid LinkRequestDto projectRequestDto) {

        UUID projectId = projectRequestDto.entityId();
        UUID organizationId = projectRequestDto.relatedEntityId();

        return projectOrganizationDataSource.exists(projectId, organizationId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteProjectOrganizationLink(@Valid LinkRequestDto projectOrganizationRequestDto) {

        UUID projectId = projectOrganizationRequestDto.entityId();
        UUID organizationId = projectOrganizationRequestDto.relatedEntityId();

        projectOrganizationDataSource.removeProjectOrganizationLink(projectId, organizationId);
    }

    @Transactional(readOnly = true)
    public List<OrganizationResponseDto> getProjectsOrganizations(UUID id) {
        return projectOrganizationDataSource.getProjectOrganizations(id);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDto> getOrganizationProjects(UUID id) {
        return projectOrganizationDataSource.getOrganizationProjects(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveUserProject(@Valid LinkRequestDto requestDto) {

        UUID projectId = requestDto.entityId();
        UUID organizationId = requestDto.relatedEntityId();

        if (exists(requestDto)) {
            throw new AlreadyExistsException(ErrorMessages.USER_PROJECT_ALREADY_EXISTS, projectId, organizationId);
        }

        userProjectDataSource.saveUserProject(projectId, organizationId);
    }

    public boolean exists(LinkRequestDto requestDto) {

        UUID projectId = requestDto.entityId();
        UUID organizationId = requestDto.relatedEntityId();

        return userProjectDataSource.existsUserProject(projectId, organizationId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeUserProject(@Valid LinkRequestDto requestDto) {

        UUID projectId = requestDto.entityId();
        UUID organizationId = requestDto.relatedEntityId();

        userProjectDataSource.removeUserProject(projectId, organizationId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancelProject(UUID id) {
        projectDataSource.cancelProject(id);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDto> getUserProjects(UUID id) {
        return userProjectDataSource.getUserProjects(id);
    }

}

