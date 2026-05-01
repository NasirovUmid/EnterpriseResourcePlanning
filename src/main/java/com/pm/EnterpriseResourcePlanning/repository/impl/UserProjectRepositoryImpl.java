package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.ProjectEntity;
import com.pm.EnterpriseResourcePlanning.enums.ProjectStatus;
import com.pm.EnterpriseResourcePlanning.repository.UserProjectRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
@RequiredArgsConstructor
public class UserProjectRepositoryImpl implements UserProjectRepository {

    private final DSLContext dsl;
    private final Table<?> tableName = table("user_projects");
    private final Field<UUID> userField = field("user_id", UUID.class);
    private final Field<UUID> projectField = field("project_id", UUID.class);
    private final Table<?> projectTable = table("projects");
    private final Field<UUID> idField = field("id", UUID.class);
    private final Field<String> nameField = field("name", String.class);
    private final Field<Object> statusField = DSL.field("status", DSL.name("project_status"));


    @Override

    public void saveUserProject(UUID userId, UUID projectId) {

        dsl.insertInto(tableName)
                .columns(userField, projectField)
                .values(userId, projectId)
                .execute();
    }

    @Override
    public boolean existsUserProject(UUID userId, UUID projectId) {
        return dsl.fetchExists(dsl.selectOne()
                .from(tableName)
                .where(userField.eq(userId))
                .and(projectField.eq(projectId)));
    }

    @Override
    public void removeUserProject(UUID userId, UUID projectId) {

        dsl.deleteFrom(tableName)
                .where(userField.eq(userId))
                .and(projectField.eq(projectId))
                .execute();
    }

    @Override
    public List<ProjectEntity> getUserProjects(UUID userId) {

        return dsl.select(projectTable.fields())
                .from(projectTable)
                // Используем строку "projects.id" для ясности и переменную projectField для связи
                .join(tableName).on(field("projects.id").eq(projectField))
                // Используем userField напрямую, без вызова tableName.field()
                .where(userField.eq(userId))
                .fetch(record ->
                        ProjectEntity.builder()
                                .id(record.get(idField))
                                .name(record.get(nameField))
                                .status(ProjectStatus.valueOf(String.valueOf(record.get(statusField))))
                                .build());
    }
}
