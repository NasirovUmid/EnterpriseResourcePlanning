package com.pm.EnterpriseResourcePlanning.mapper;

import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ClientRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ClientResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ClientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientEntity toEntity(ClientRequestDto clientRequestDto);

    ClientResponseDto toDto(ClientEntity clientEntity);
}
