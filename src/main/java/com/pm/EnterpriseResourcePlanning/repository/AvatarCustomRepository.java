package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.AvatarEntity;

import java.util.Optional;
import java.util.UUID;

public interface AvatarCustomRepository {

    Optional<AvatarEntity> saveAvatar(String url);

    Optional<AvatarEntity> getAvatarById(UUID id);
}
