package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.AvatarResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.AvatarEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AvatarDataSource {


    AvatarEntity saveAvatar(String url, UUID userId);

    void updateAvatar(String url, UUID id);

    AvatarResponseDto getAvatarById(UUID id);

    AvatarResponseDto getAvatarByUserId(UUID id);

}
