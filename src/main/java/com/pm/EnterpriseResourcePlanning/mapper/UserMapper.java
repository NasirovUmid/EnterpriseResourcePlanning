package com.pm.EnterpriseResourcePlanning.mapper;

import com.pm.EnterpriseResourcePlanning.dto.requestdtos.UserRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.UserResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(UserRequestDto userRequestDto);

    @Mapping(target = "phone", source = "phoneNumber")
    @Mapping(target = "status", source = "userStatus")
    UserResponseDto toDto(UserEntity user);
}
