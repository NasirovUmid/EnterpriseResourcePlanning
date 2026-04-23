package com.pm.EnterpriseResourcePlanning.mapper;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ActionResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ActionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActionMapper {

    ActionResponseDto toDto(ActionEntity actionEntity);
}
