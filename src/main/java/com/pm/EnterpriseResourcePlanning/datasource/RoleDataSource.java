package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.RoleResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.RolesEntity;
import com.pm.EnterpriseResourcePlanning.enums.RoleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleDataSource {

    RoleResponseDto saveRole(String name, RoleStatus status);

    List<RoleResponseDto> getRolePages();

    void deactivateRole(UUID id);

    RolesEntity getRoleById(UUID id);
}
