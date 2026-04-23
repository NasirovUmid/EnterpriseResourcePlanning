package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.RefreshTokenEntity;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository {

    void saveRefreshToken(UUID userId, String token, Instant expiryDate);

    Optional<RefreshTokenEntity> getRefreshByToken(String token);

    void deleteByToken(String token);

    void deleteAllByUserId(UUID userId);

    int deleteExpired();
}
