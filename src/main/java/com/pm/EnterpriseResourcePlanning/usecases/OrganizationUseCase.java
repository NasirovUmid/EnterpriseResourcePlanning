package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.dao.UserDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.UserDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.OrganizationDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.UserOrganizationDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.SortResolver;
import com.pm.EnterpriseResourcePlanning.datasource.impl.OrganizationDataSourceImpl;
import com.pm.EnterpriseResourcePlanning.datasource.impl.UserOrganizationDataSourceImpl;
import com.pm.EnterpriseResourcePlanning.dto.filters.OrganizationFilterDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.LinkRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.OrganizationRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.OrganizationUpdateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.OrganizationResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.UserResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.OrganizationEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.enums.SortType;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationUseCase {

    private final OrganizationDataSource organizationDataSource;
    private final UserOrganizationDataSource userOrganizationDataSource;
    private final UserDao userDao;

    @Transactional(rollbackFor = Exception.class)
    public OrganizationResponseDto createOrganization(@Valid OrganizationRequestDto organizationRequestDto) {

        if (organizationDataSource.existsByInn(organizationRequestDto.inn())) {
            throw new AlreadyExistsException(ErrorMessages.USER_ALREADY_EXISTS, organizationRequestDto.inn());
        }

        return organizationDataSource.saveOrganization(organizationRequestDto.name(), organizationRequestDto.inn(), organizationRequestDto.address());
    }

    @Transactional(readOnly = true)
    public OrganizationResponseDto getOrganizationById(UUID id) {
        return organizationDataSource.getOrganizationById(id);
    }

    @Transactional(readOnly = true)
    public Page<OrganizationResponseDto> getOrganizationsPage(int page, int size, String sort, OrganizationFilterDto organizationFilterDto) {

        Specification<OrganizationEntity> specification = OrganizationSpecifications.build(organizationFilterDto.name(), organizationFilterDto.inn(), organizationFilterDto.address());

        Sort sort1 = SortResolver.resolver(SortType.ORGANIZATION, sort);

        Pageable pageable = PageRequest.of(page, size, sort1);

        return organizationDataSource.getOrganizationsPage(specification, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateOrganization(UUID id, OrganizationUpdateRequestDto organizationUpdateRequestDto) {
        organizationDataSource.updateOrganization(organizationUpdateRequestDto.name(), organizationUpdateRequestDto.address(), id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void createUserOrganizationLink(@Valid LinkRequestDto userOrganizationRequestDto) {

        UUID userId = userOrganizationRequestDto.entityId();
        UUID organizationId = userOrganizationRequestDto.relatedEntityId();

        if (userDao.getUserById(userId).getUserStatus().equals(UserStatus.DEACTIVATED)) {
            throw new IllegalStateException(ErrorMessages.USER_IS_DEACTIVATED, userId, organizationId);
        }

        if (exists(userOrganizationRequestDto)) {
            throw new AlreadyExistsException(ErrorMessages.USER_ORGANIZATION_ALREADY_EXISTS, userId, organizationId);
        }

        userOrganizationDataSource.saveUserOrganization(userId, organizationId);
    }

    public boolean exists(@Valid LinkRequestDto userOrganizationRequestDto) {

        UUID userId = userOrganizationRequestDto.entityId();
        UUID organizationId = userOrganizationRequestDto.relatedEntityId();

        return userOrganizationDataSource.exists(userId, organizationId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeUserOrganizationLink(@Valid LinkRequestDto userOrganizationRequestDto) {

        UUID userId = userOrganizationRequestDto.entityId();
        UUID organizationId = userOrganizationRequestDto.relatedEntityId();

        userOrganizationDataSource.removeUserOrganizationLink(userId, organizationId);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getOrganizationUsers(UUID id) {
        return userOrganizationDataSource.getOrganizationUsers(id);
    }

    @Transactional(readOnly = true)
    public List<OrganizationResponseDto> getUserOrganizations(UUID userId) {
        return userOrganizationDataSource.getUserOrganizations(userId);
    }
}

