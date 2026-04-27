package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.AvatarEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AvatarDao {

    AvatarEntity saveAvatar(String url, UUID userId);

    Page<AvatarEntity> getAvatarsPage(Pageable pageable);

    void updateAvatar(String url, UUID id);

    AvatarEntity getAvatarById(UUID id);

}
