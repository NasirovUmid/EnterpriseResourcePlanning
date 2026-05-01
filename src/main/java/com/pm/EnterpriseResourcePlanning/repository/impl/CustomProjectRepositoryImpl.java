package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.ProjectEntity;
import com.pm.EnterpriseResourcePlanning.entity.UserEntity;
import com.pm.EnterpriseResourcePlanning.enums.ProjectStatus;
import com.pm.EnterpriseResourcePlanning.enums.UserStatus;
import com.pm.EnterpriseResourcePlanning.repository.CustomProjectRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import static org.jooq.impl.DSL.*;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomProjectRepositoryImpl implements CustomProjectRepository {

    private final DSLContext dsl;
    private final Table<?> tableName = table("projects");
    private final Field<String> nameField = field("name", String.class);
    private final Field<Object> statusField = field("status", DSL.name("project_status"));
    private final Field<UUID> idField = field("id", UUID.class);

    @Override
    public Optional<ProjectEntity> save(String name) {

        var record = dsl.insertInto(tableName)
                .columns(nameField, statusField)
                .values(name, DSL.field("?::project_status", ProjectStatus.AWAITING.name()))
                .returning(idField, nameField, statusField)
                .fetchOne();

        System.out.println(record);

        if (record == null) {
            return Optional.empty();
        }

        ProjectEntity projectEntity = ProjectEntity.builder()
                .id(record.get(idField))
                .name(record.get(nameField))
                .status(ProjectStatus.valueOf(String.valueOf(record.get(statusField))))
                .build();

        return Optional.of(projectEntity);
    }
}
