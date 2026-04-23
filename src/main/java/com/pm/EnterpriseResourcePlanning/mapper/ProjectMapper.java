package com.pm.EnterpriseResourcePlanning.mapper;

import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ProjectRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProjectResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ProjectEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectEntity toEntity(ProjectRequestDto projectRequestDto);

    ProjectResponseDto toDto(ProjectEntity projectEntity);
}
