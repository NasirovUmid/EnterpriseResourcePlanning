package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.RolesEntity;
import com.pm.EnterpriseResourcePlanning.enums.RoleStatus;
import com.pm.EnterpriseResourcePlanning.repository.UserRolesRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

import static org.jooq.impl.DSL.*;

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
                .fetch(record -> new RolesEntity(
                        record.get(field("id", UUID.class)),
                        record.get(field("name", String.class)),
                        record.get(field("status")) != null ? RoleStatus.valueOf(String.valueOf(record.get(field("status")))) : null
                ));
    }

    @Override
    public List<String> findAllAuthoritiesByUserId(UUID userId) {
        return dsl.selectDistinct( // selectDistinct, чтобы не дублировать права, если они есть в разных ролях
                        field(name("modules", "name")).concat(":").concat(field(name("actions", "name")))
                )
                .from(table("user_roles"))
                .join(table("role_permissions")).on(field(name("user_roles", "role_id")).eq(field(name("role_permissions", "role_id"))))
                .join(table("permissions")).on(field(name("role_permissions", "permission_id")).eq(field(name("permissions", "id"))))
                .join(table("modules")).on(field(name("permissions", "module_id")).eq(field(name("modules", "id"))))
                .join(table("actions")).on(field(name("permissions", "action_id")).eq(field(name("actions", "id"))))
                .where(field(name("user_roles", "user_id")).eq(userId))
                .fetchInto(String.class);
    }
}