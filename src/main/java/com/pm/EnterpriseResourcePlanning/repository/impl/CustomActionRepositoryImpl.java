package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.ActionEntity;
import com.pm.EnterpriseResourcePlanning.repository.CustomActionRepository;
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
public class CustomActionRepositoryImpl implements CustomActionRepository {

    private final DSLContext dsl;
    private final Table<?> tableName = table("actions");
    private final Field<String> nameField = field("name", String.class);
    private final Field<UUID> idField = field("id", UUID.class);

    @Override
    public Optional<ActionEntity> save(String name) {

        var record = dsl.insertInto(tableName)
                .columns(nameField)
                .values(name)
                .returning(idField, nameField)
                .fetchOne();

        if (record == null) {
            return Optional.empty();
        }

        return Optional.of(new ActionEntity(
                record.get(idField),
                record.get(nameField)
        ));
    }
}
