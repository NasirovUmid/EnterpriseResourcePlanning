package com.pm.EnterpriseResourcePlanning.mapper;

import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ContractsRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ContractResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    ContractsEntity toEntity(ContractsRequestDto contractsRequestDto);

    @Mapping(source = "amount", target = "price")
    ContractResponseDto toDto(ContractsEntity contractsEntity);
}
