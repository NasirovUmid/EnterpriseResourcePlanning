package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.RefreshTokenDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.RefreshTokenDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.entity.RefreshTokenEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class RefreshTokenDataSourceImpl extends MessageAlertDataSource implements RefreshTokenDataSource {

    private final RefreshTokenDaoImpl refreshTokenDao;

    public RefreshTokenDataSourceImpl(AlertSystemDao alertSystemDao, RefreshTokenDaoImpl refreshTokenDao) {
        super(alertSystemDao, RefreshTokenDataSourceImpl.class);
        this.refreshTokenDao = refreshTokenDao;
    }

    @Override
    public void saveRefreshToken(UUID userId, String token, Instant expiryDate) {
        execute(() -> refreshTokenDao.saveRefreshToken(userId, token, expiryDate));
    }

    @Override
    public RefreshTokenEntity getRefreshByToken(String token) {
        return execute(() -> refreshTokenDao.getRefreshByToken(token));
    }

    @Override
    public void deleteByToken(String token) {
        execute(() -> refreshTokenDao.deleteByToken(token));
    }

    @Override
    public void deleteAllByUserId(UUID userId) {
        execute(() -> refreshTokenDao.deleteAllByUserId(userId));
    }

    @Override
    public int deleteExpired() {
        return execute(refreshTokenDao::deleteExpired);
    }
}
