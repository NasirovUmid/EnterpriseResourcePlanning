package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.AvatarEntity;
import com.pm.EnterpriseResourcePlanning.repository.AvatarCustomRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
@RequiredArgsConstructor
public class AvatarCustomRepositoryImpl implements AvatarCustomRepository {

    private final DSLContext dsl;
    private final Table<?> tableName = table("avatars");
    private final Field<String> urlField = field("url", String.class);
    private final Field<UUID> avatarField = field("id", UUID.class);
    private final Field<UUID> userField = field("user_id", UUID.class);

    @Override
    public Optional<AvatarEntity> saveAvatar(String url, UUID userId) {
        var record = dsl.insertInto(tableName)
                .columns(urlField, userField)
                .values(url, userId)
                .returning(avatarField, urlField)
                .fetchOne();

        System.out.println(record);

        if (record == null) return Optional.empty();

        AvatarEntity entity = new AvatarEntity();
        entity.setId(record.get(avatarField));
        entity.setUrl(record.get(urlField));
        return Optional.of(entity);

    }

    @Override
    public Optional<AvatarEntity> getAvatarById(UUID id) {
        return dsl.select(tableName)
                .from(tableName)
                .where(avatarField.eq(id))
                .fetchOptionalInto(AvatarEntity.class);
    }

    @Override
    public Optional<AvatarEntity> getAvatarByUserId(UUID userId) {

        return dsl.select(tableName)
                .from(tableName)
                .where(userField.eq(userId))
                .fetchOptionalInto(AvatarEntity.class);

    }
}