package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import com.pm.EnterpriseResourcePlanning.entity.ProjectEntity;
import com.pm.EnterpriseResourcePlanning.enums.ProjectStatus;
import com.pm.EnterpriseResourcePlanning.repository.ContractProjectsRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
@RequiredArgsConstructor
public class ContractProjectsRepositoryImpl implements ContractProjectsRepository {

    private final DSLContext dsl;
    private final Table<?> tableName = table("contract_projects");
    private final Field<UUID> contractIdField = field("contract_id", UUID.class);
    private final Field<UUID> projectIdField = field("project_id", UUID.class);


    @Override
    public void saveContractProjects(UUID contractId, UUID projectId) {
        dsl.insertInto(tableName)
                .columns(contractIdField, projectIdField)
                .values(contractId, projectId)
                .execute();
    }

    @Override
    public void deleteContractProjects(UUID contractId, UUID projectId) {
        dsl.deleteFrom(tableName)
                .where(contractIdField.eq(contractId).and(projectIdField.eq(projectId)))
                .execute();
    }

    @Override
    public boolean exists(UUID contractId, UUID projectId) {
        return dsl.fetchExists(dsl.selectOne()
                .from(tableName)
                .where(contractIdField.eq(contractId))
                .and(projectIdField.eq(projectId)));
    }

    @Override
    public List<ProjectEntity> getProjectsByContractId(UUID contractId) {
        return dsl.select(table("projects").fields()) // 1. Что берем? Все поля проектов
                .from(table("projects"))             // 2. Откуда?
                .join(table("contract_projects"))    // 3. С чем клеим? С таблицей связей
                .on(field("projects.id").eq(field("contract_projects.project_id"))) // 4. По какому полю?
                .where(field("contract_projects.contract_id").eq(contractId)) // 5. Условие
                .fetch(record -> ProjectEntity.builder()
                        .id(record.get(field("id", UUID.class)))
                        .name(record.get(field("name", String.class)))
                        .status(ProjectStatus.valueOf(record.get(field("status", String.class))))
                        .build());
    }

    @Override
    public List<ContractsEntity> getContractsByProjectId(UUID projectId) {
        return dsl.select(table("contracts").fields())
                .from(table("contracts"))
                .join(table("contract_projects"))
                .on(field("contracts.id").eq(field("contract_projects.contract_id")))
                .where(field("contract_projects.project_id").eq(projectId))
                .fetch(record -> ContractsEntity.builder()
                        .id(record.get(field("id", UUID.class)))
                        .contractNumber(record.get(field("contract_number", String.class)))
                        .amount(record.get(field("amount", Double.class)))
                        .startDate(record.get(field("start_date", Instant.class)))
                        .endDate(record.get(field("end_date", Instant.class)))
                        .build());
    }
}
