package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.PermissionResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.PermissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface PermissionDataSource {

    List<String> findAllPermissionNamesByUserId(UUID userId);

    PermissionResponseDto savePermission(String name, UUID moduleId, UUID actionId);

    Page<PermissionResponseDto> getPermissionPage(Specification<PermissionEntity> specification, Pageable pageable);

    void updatePermission(String name, UUID moduleId, UUID actionId, UUID id);

    PermissionResponseDto getPermissionById(UUID id);

}
