package com.pm.EnterpriseResourcePlanning.mapper;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ModuleResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ModuleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModuleMapper {

    ModuleResponseDto toDto(ModuleEntity moduleEntity);
}
