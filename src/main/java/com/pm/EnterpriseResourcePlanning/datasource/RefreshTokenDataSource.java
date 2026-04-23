package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.entity.RefreshTokenEntity;

import java.time.Instant;
import java.util.UUID;

public interface RefreshTokenDataSource {

    void saveRefreshToken(UUID userId, String token, Instant expiryDate);

    RefreshTokenEntity getRefreshByToken(String token);

    void deleteByToken(String token);

    void deleteAllByUserId(UUID userId);

    int deleteExpired();
}
