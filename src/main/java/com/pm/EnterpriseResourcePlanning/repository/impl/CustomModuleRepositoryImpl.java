package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.ModuleEntity;
import com.pm.EnterpriseResourcePlanning.repository.CustomModuleRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static org.jooq.impl.DSL.*;

@Repository
@RequiredArgsConstructor
public class CustomModuleRepositoryImpl implements CustomModuleRepository {

    private final DSLContext dsl;
    private final Table<?> tableName = table("modules");
    private final Field<UUID> idField = field("id", UUID.class);
    private final Field<String> nameField = field("name", String.class);

    @Override
    public Optional<ModuleEntity> saveModule(String name) {

        var record = dsl.insertInto(tableName)
                .columns(nameField)
                .values(name)
                .returning(idField, nameField)
                .fetchOne();

        if (record == null) {
            return Optional.empty();
        }

        return Optional.of(new ModuleEntity(
                record.get(idField),
                record.get(nameField)
        ));
    }
}
