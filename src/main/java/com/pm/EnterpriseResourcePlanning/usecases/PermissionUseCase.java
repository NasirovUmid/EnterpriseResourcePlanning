package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.datasource.PermissionDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.SortResolver;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.PermissionRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.PermissionUpdateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.PermissionResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.PermissionEntity;
import com.pm.EnterpriseResourcePlanning.enums.SortType;
import com.pm.EnterpriseResourcePlanning.specifications.PermissionSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionUseCase {

    private final PermissionDataSource dataSource;

    @Transactional(rollbackFor = Exception.class)
    public PermissionResponseDto createPermission(@Valid PermissionRequestDto permissionRequestDto) {

        return dataSource.savePermission(permissionRequestDto.name(), permissionRequestDto.moduleId(), permissionRequestDto.actionId());
    }

    @Transactional(readOnly = true)
    public Page<PermissionResponseDto> getPermissionPages(int page, int size, String sort, String name) {

        Specification<PermissionEntity> specification = PermissionSpecification.build(name);

        Sort sort1 = SortResolver.resolver(SortType.PERMISSION,sort);

        Pageable pageable = PageRequest.of(page, size, sort1);

        return dataSource.getPermissionPage(specification, pageable);
    }

    @Transactional(readOnly = true)
    public PermissionResponseDto getPermissionById(UUID id) {
        return dataSource.getPermissionById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updatePermission(UUID id, @Valid PermissionUpdateRequestDto updateRequestDto) {
        dataSource.updatePermission(updateRequestDto.name(), updateRequestDto.moduleId(), updateRequestDto.actionId(), id);
    }
}
