package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.AvatarDao;
import com.pm.EnterpriseResourcePlanning.entity.AvatarEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.exceptions.NotFoundException;
import com.pm.EnterpriseResourcePlanning.repository.AvatarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AvatarDaoImpl implements AvatarDao {

    private final AvatarRepository repository;

    @Override
    public AvatarEntity saveAvatar(String url, UUID userId) {
        return repository.saveAvatar(url, userId).orElseThrow(RuntimeException::new);
    }

    @Override
    public Page<AvatarEntity> getAvatarsPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void updateAvatar(String url, UUID id) {
        repository.updateAvatar(url, id);
    }

    @Override
    public AvatarEntity getAvatarById(UUID id) {
        return repository.getAvatarById(id).orElseThrow(() -> new NotFoundException(ErrorMessages.AVATAR_NOT_FOUND, id));
    }
}
