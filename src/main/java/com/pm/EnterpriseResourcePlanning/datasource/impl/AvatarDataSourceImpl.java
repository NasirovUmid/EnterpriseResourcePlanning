package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.AvatarDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.AvatarDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.AvatarResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.AvatarEntity;
import com.pm.EnterpriseResourcePlanning.mapper.AvatarMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AvatarDataSourceImpl extends MessageAlertDataSource implements AvatarDataSource {

    private final AvatarDaoImpl avatarDao;
    private final AvatarMapper avatarMapper;

    public AvatarDataSourceImpl(AlertSystemDao alertSystemDao, AvatarDaoImpl avatarDao, AvatarMapper avatarMapper) {
        super(alertSystemDao, AvatarDataSourceImpl.class);
        this.avatarDao = avatarDao;
        this.avatarMapper = avatarMapper;
    }

    @Override
    public AvatarEntity saveAvatar(String url, UUID userId) {
        return execute(() -> avatarDao.saveAvatar(url, userId));
    }

    @Override
    public void updateAvatar(String url, UUID id) {
        execute(() -> avatarDao.updateAvatar(url, id));
    }

    @Override
    public AvatarResponseDto getAvatarById(UUID id) {
        return execute(() -> avatarMapper.toDto(avatarDao.getAvatarById(id)));
    }

    @Override
    public AvatarResponseDto getAvatarByUserId(UUID id) {
        return execute(() -> avatarMapper.toDto(avatarDao.getAvatarByUserId(id)));
    }
}
