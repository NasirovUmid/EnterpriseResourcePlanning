package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.RefreshTokenDao;
import com.pm.EnterpriseResourcePlanning.entity.RefreshTokenEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.exceptions.NotFoundException;
import com.pm.EnterpriseResourcePlanning.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RefreshTokenDaoImpl implements RefreshTokenDao {

    private final RefreshTokenRepository repository;

    @Override
    public void saveRefreshToken(UUID userId, String token, Instant expiryDate) {
        repository.saveRefreshToken(userId, token, expiryDate);
    }

    @Override
    public RefreshTokenEntity getRefreshByToken(String token) {
        return repository.getRefreshByToken(token).orElseThrow(() -> new NotFoundException(ErrorMessages.TOKEN_NOT_FOUND, token));
    }

    @Override
    public void deleteByToken(String token) {
        repository.deleteByToken(token);
    }

    @Override
    public void deleteAllByUserId(UUID userId) {
        repository.deleteAllByUserId(userId);
    }

    @Override
    public int deleteExpired() {
        return repository.deleteExpired();
    }
}
