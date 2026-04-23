package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.RoleResponseDto;
import com.pm.EnterpriseResourcePlanning.enums.RoleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RoleDataSource {

    RoleResponseDto saveRole(String name, RoleStatus status);

    Page<RoleResponseDto> getRolePages(Pageable pageable);

    void deactivateRole(UUID id);

    RoleResponseDto getRoleById(UUID id);


}
