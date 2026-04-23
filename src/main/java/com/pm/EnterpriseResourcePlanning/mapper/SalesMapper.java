package com.pm.EnterpriseResourcePlanning.mapper;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.SalesResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.SalesEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SalesMapper {

    SalesResponseDto toDto(SalesEntity salesEntity);

}
