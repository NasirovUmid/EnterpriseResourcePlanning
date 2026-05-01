package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.PermissionEntity;
import com.pm.EnterpriseResourcePlanning.repository.RolePermissionsRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
@RequiredArgsConstructor
public class RolePermissionRepositoryImpl implements RolePermissionsRepository {

    private final DSLContext dsl;
    private final Table<?> tableName = table("role_permissions");
    private final Field<UUID> roleField = field("role_id", UUID.class);
    private final Field<UUID> permissionField = field("permission_id", UUID.class);
    private final Table<?> pTable = table("permissions");

    @Transactional
    @Override
    public void saveRolePermissions(UUID roleId, UUID permissionId) {

        dsl.insertInto(tableName).
                columns(roleField, permissionField)
                .values(roleId, permissionId)
                .execute();
    }

    @Override
    public boolean exists(UUID roleId, UUID permissionId) {
        return dsl.fetchExists(dsl.selectOne()
                .from(tableName)
                .where(roleField.eq(roleId))
                .and(permissionField.eq(permissionId)));
    }

    @Transactional
    @Override
    public void removeUserRoleLink(UUID roleId, UUID permissionId) {

        dsl.deleteFrom(table())
                .where(roleField.eq(roleId))
                .and(permissionField.eq(permissionField))
                .execute();

    }

    @Transactional(readOnly = true)
    @Override
    public List<PermissionEntity> getRolePermissions(UUID roleId) {
        return dsl.select(pTable.fields())
                .from(pTable)
                .join(tableName).on(field("permissions.id", UUID.class).eq(permissionField))
                .where(roleField.eq(roleId))
                .fetch(record -> new PermissionEntity(
                        record.get(field("id", UUID.class)),
                        record.get(field("name", String.class)),
                        record.get(field("module_id", UUID.class)),
                        record.get(field("action_id", UUID.class))
                ));
    }
}
