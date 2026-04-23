package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.RefreshTokenEntity;
import com.pm.EnterpriseResourcePlanning.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.jooq.impl.DSL.*;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final DSLContext dsl;
    Table<?> tableName = table("refresh_tokens");
    Field<UUID> userField = field("user_id", UUID.class);
    Field<String> tokenField = field("token", String.class);
    Field<Instant> dateField = field("expiry_date", Instant.class);


    @Override
    public void saveRefreshToken(UUID userId, String token, Instant expiryDate) {

        dsl.insertInto(tableName)
                .columns(userField, tokenField, dateField)
                .values(userId, token, expiryDate)
                .execute();

    }

    @Override
    public Optional<RefreshTokenEntity> getRefreshByToken(String token) {
        return dsl.selectFrom(tableName)
                .where(tokenField.eq(token))
                .fetchOptionalInto(RefreshTokenEntity.class);
    }

    @Override
    public void deleteByToken(String token) {

        dsl.deleteFrom(tableName)
                .where(tokenField.eq(token))
                .execute();

    }

    @Override
    public void deleteAllByUserId(UUID userId) {

        dsl.delete(tableName)
                .where(userField.eq(userId))
                .execute();

    }

    @Override
    public int deleteExpired() {

        return dsl.deleteFrom(tableName)
                .where(dateField.lt(Instant.now()))
                .execute();
    }


}
