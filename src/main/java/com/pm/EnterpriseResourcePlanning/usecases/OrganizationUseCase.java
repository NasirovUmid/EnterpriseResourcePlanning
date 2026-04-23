package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.dao.impl.UserDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.impl.OrganizationDataSourceImpl;
import com.pm.EnterpriseResourcePlanning.datasource.impl.UserOrganizationDataSourceImpl;
import com.pm.EnterpriseResourcePlanning.dto.filters.OrganizationFilterDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.OrganizationRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.OrganizationUpdateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.OrganizationResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.UserResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.OrganizationEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.enums.UserStatus;
import com.pm.EnterpriseResourcePlanning.exceptions.AlreadyExistsException;
import com.pm.EnterpriseResourcePlanning.exceptions.IllegalStateException;
import com.pm.EnterpriseResourcePlanning.specifications.OrganizationSpecifications;
import jakarta.validation.Valid;
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
public class OrganizationUseCase {

    private final OrganizationDataSourceImpl organizationDataSource;
    private final UserOrganizationDataSourceImpl userOrganizationDataSource;
    private final UserDaoImpl userDao;

    public OrganizationResponseDto createOrganization(@Valid OrganizationRequestDto organizationRequestDto) {

        if (organizationDataSource.existsByInn(organizationRequestDto.inn())) {
            throw new AlreadyExistsException(ErrorMessages.USER_ALREADY_EXISTS, organizationRequestDto.inn());
        }

        return organizationDataSource.saveOrganization(organizationRequestDto.name(), organizationRequestDto.inn(), organizationRequestDto.address());
    }

    public OrganizationResponseDto getOrganizationById(UUID id) {
        return organizationDataSource.getOrganizationById(id);
    }

    public Page<OrganizationResponseDto> getOrganizationsPage(int page, int size, String sort, OrganizationFilterDto organizationFilterDto) {

        Specification<OrganizationEntity> specification = OrganizationSpecifications.build(organizationFilterDto.name(), organizationFilterDto.inn(), organizationFilterDto.address());
        Pageable pageable = PageRequest.of(page, size, toOrganizationEntitySort(sort));

        return organizationDataSource.getOrganizationsPage(specification, pageable);
    }

    public void updateOrganization(UUID id, OrganizationUpdateRequestDto organizationUpdateRequestDto) {
        organizationDataSource.updateOrganization(organizationUpdateRequestDto.name(), organizationUpdateRequestDto.address(), id);
    }

    public void createUserOrganizationLink(@Valid IntermediateRequestDto userOrganizationRequestDto) {

        if (userDao.getUserById(userOrganizationRequestDto.uuid()).getUserStatus().equals(UserStatus.DEACTIVATED)) {
            throw new IllegalStateException(ErrorMessages.USER_IS_DEACTIVATED, userOrganizationRequestDto.uuid(), userOrganizationRequestDto.uuid1());
        }

        if (exists(userOrganizationRequestDto)) {
            throw new AlreadyExistsException(ErrorMessages.USER_ORGANIZATION_ALREADY_EXISTS, userOrganizationRequestDto.uuid(), userOrganizationRequestDto.uuid1());
        }

        userOrganizationDataSource.saveUserOrganization(userOrganizationRequestDto.uuid(), userOrganizationRequestDto.uuid1());
    }

    public boolean exists(@Valid IntermediateRequestDto userOrganizationRequestDto) {
        return userOrganizationDataSource.exists(userOrganizationRequestDto.uuid(), userOrganizationRequestDto.uuid1());
    }

    public void removeUserOrganizationLink(@Valid IntermediateRequestDto userOrganizationRequestDto) {
        userOrganizationDataSource.removeUserOrganizationLink(userOrganizationRequestDto.uuid(), userOrganizationRequestDto.uuid1());
    }

    public List<UserResponseDto> getOrganizationUsers(UUID id) {
        return userOrganizationDataSource.getOrganizationUsers(id);
    }

    private Sort toOrganizationEntitySort(String sort) {

        if (sort == null || !sort.contains(",")) {
            return Sort.by("name").ascending();
        }
        String[] parts = sort.split(",");
        String field = parts[0];
        String direction = parts[1];

        return direction.equalsIgnoreCase("desc")
                ? Sort.by(field).descending()
                : Sort.by(field).ascending();
    }
}

