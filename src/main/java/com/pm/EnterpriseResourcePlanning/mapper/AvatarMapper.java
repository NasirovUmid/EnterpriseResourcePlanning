package com.pm.EnterpriseResourcePlanning.mapper;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.AvatarResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.AvatarEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AvatarMapper {

    AvatarResponseDto toDto(AvatarEntity avatarEntity);
}
