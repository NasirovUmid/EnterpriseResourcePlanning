package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ModuleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ModuleDataSource {

    ModuleResponseDto saveModule(String name);

    Page<ModuleResponseDto> getModuleEntities(Pageable pageable);

    void updateModule(String name, UUID id);

    ModuleResponseDto getModuleById(UUID id);

}
