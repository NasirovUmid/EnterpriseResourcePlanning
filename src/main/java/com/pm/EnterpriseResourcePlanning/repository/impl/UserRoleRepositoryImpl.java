package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.RolesEntity;
import com.pm.EnterpriseResourcePlanning.repository.UserRolesRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
@RequiredArgsConstructor
public class UserRoleRepositoryImpl implements UserRolesRepository {

    private final DSLContext dsl;
    private final String tableName = "user_roles";
    private final String roleName = "roles";
    private final Field<UUID> userField = field("user_id", UUID.class);
    private final Field<UUID> roleField = field("role_id", UUID.class);

    @Override
    @Transactional
    public void saveUserRole(UUID userId, UUID roleId) {

        dsl.insertInto(table(tableName))
                .columns(field(userField), field(roleField))
                .values(userId, roleId)
                .execute();
    }

    @Override
    @Transactional
    public boolean exists(UUID userId, UUID roleId) {
        return dsl.fetchExists(
                dsl.selectOne()
                        .from(table(tableName))
                        .where(userField.eq(userId))
                        .and(roleField.eq(roleId))
        );
    }

    @Override
    @Transactional
    public void removeUserRoleLink(UUID userId, UUID roleId) {

        dsl.deleteFrom(table(tableName))
                .where(userField.eq(userId))
                .and(roleField.eq(roleId))
                .execute();

    }

    @Override
    public List<RolesEntity> findRolesByUserId(UUID userId) {
        return dsl.select(table(roleName).fields())
                .from(table(roleName))
                .join(table(tableName)).on(field("roles.id").eq(field("user_roles.role_id")))
                .where(field(tableName + ".user_id", UUID.class).eq(userId))
                .fetchInto(RolesEntity.class);
    }


}
