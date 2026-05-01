package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.RolesEntity;
import com.pm.EnterpriseResourcePlanning.enums.RoleStatus;
import com.pm.EnterpriseResourcePlanning.repository.CustomRoleRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
@RequiredArgsConstructor
public class CustomRoleRepositoryImpl implements CustomRoleRepository {

    private final DSLContext dsl;
    private final Table<?> tableName = table("roles");
    private final Field<String> nameField = field("name", String.class);
    private final Field<Object> statusField = field("status", DSL.name("role_status"));
    private final Field<UUID> idField = field("id", UUID.class);

    @Override
    public Optional<RolesEntity> saveRole(String name, RoleStatus status) {

        var record = dsl.insertInto(tableName)
                .columns(nameField, statusField)
                .values(name, DSL.field("?::role_status", status))
                .returning(idField, nameField, statusField)
                .fetchOne();

        if (record == null) {
            return Optional.empty();
        }

        return Optional.of(new RolesEntity(
                record.get(idField),
                record.get(nameField),
                RoleStatus.valueOf(String.valueOf(record.get(statusField)))
        ));

    }
}
