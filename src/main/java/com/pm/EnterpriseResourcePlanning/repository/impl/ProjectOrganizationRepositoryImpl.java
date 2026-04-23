package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.OrganizationEntity;
import com.pm.EnterpriseResourcePlanning.entity.ProjectEntity;
import com.pm.EnterpriseResourcePlanning.repository.ProjectOrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.jooq.impl.DSL.*;

@Repository
@RequiredArgsConstructor
public class ProjectOrganizationRepositoryImpl implements ProjectOrganizationRepository {

    private final DSLContext dsl;
    private final Table<?> tableName = table("project_organization");
    private final Field<UUID> projectField = field("project_id", UUID.class);
    private final Field<UUID> organizationField = field("organization_id", UUID.class);
    private final Table<?> oTable = table("organizations");
    private final Table<?> pTable = table("projects");

    @Transactional
    @Override
    public void linkProjectOrganization(UUID projectId, UUID organizationId) {

        dsl.insertInto(tableName)
                .columns(projectField, organizationField)
                .values(projectId, organizationId)
                .execute();
    }

    @Override
    public boolean exists(UUID projectId, UUID organizationId) {
        return dsl.fetchExists(dsl.selectOne()
                .from(tableName)
                .where(projectField.eq(projectId))
                .and(organizationField.eq(organizationId)));
    }

    @Transactional
    @Override
    public void removeProjectOrganizationLink(UUID projectId, UUID organizationId) {

        dsl.deleteFrom(tableName)
                .where(projectField.eq(projectId))
                .and(organizationField.eq(organizationId))
                .execute();
    }

    @Override
    public List<OrganizationEntity> getProjectOrganizations(UUID projectId) {
        return dsl.select(oTable.fields())
                .from(oTable)
                .join(tableName).on(field("organizations.id").eq(tableName.field(organizationField)))
                .where(tableName.field(projectField).eq(projectId))
                .fetchInto(OrganizationEntity.class);
    }

    @Override
    public List<ProjectEntity> getOrganizationProjects(UUID organizationId) {
        return dsl.select(pTable.fields())
                .from(pTable)
                .join(tableName).on(field("projects.id").eq(tableName.field(projectField)))
                .where(tableName.field(organizationField).eq(organizationId))
                .fetchInto(ProjectEntity.class);
    }
}
