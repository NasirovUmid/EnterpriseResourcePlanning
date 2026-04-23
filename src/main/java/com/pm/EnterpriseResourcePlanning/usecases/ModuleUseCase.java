package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.datasource.impl.ModuleDataSourceImpl;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ModuleRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ModuleResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ModuleUseCase {

    private final ModuleDataSourceImpl moduleDataSource;

    public ModuleResponseDto createModule(@Valid ModuleRequestDto moduleRequestDto) {
        return moduleDataSource.saveModule(moduleRequestDto.name());
    }

    public Page<ModuleResponseDto> getModulePages(int page, int size) {
        return moduleDataSource.getModuleEntities(PageRequest.of(page, size));
    }

    public void updateModule(UUID id, String name) {

        moduleDataSource.updateModule(name, id);
    }

    public ModuleResponseDto getModuleById(UUID id) {
        return moduleDataSource.getModuleById(id);
    }
}
