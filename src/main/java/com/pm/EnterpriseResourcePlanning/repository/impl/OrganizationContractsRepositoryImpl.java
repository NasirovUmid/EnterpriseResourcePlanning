package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import com.pm.EnterpriseResourcePlanning.repository.OrganizationContractsRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.jooq.impl.DSL.*;

@Repository
@RequiredArgsConstructor
public class OrganizationContractsRepositoryImpl implements OrganizationContractsRepository {

    private final DSLContext dsl;
    private final Table<?> tableName = table("organization_contract");
    private final Field<UUID> organizationField = field("organization_id", UUID.class);
    private final Field<UUID> contractField = field("contract_id", UUID.class);
    private final Table<?> cTable = table("contracts");

    @Override
    public void linkOrganizationContract(UUID organizationId, UUID contractId) {
        dsl.insertInto(tableName)
                .columns(organizationField, contractField)
                .values(organizationId, contractId)
                .execute();
    }

    @Override
    public boolean exists(UUID organizationId, UUID contractId) {
        return dsl.fetchExists(dsl.selectOne()
                .from(tableName)
                .where(organizationField.eq(organizationId))
                .and(contractField.eq(contractId)));
    }

    @Override
    public void removeOrganizationContractLink(UUID organizationId, UUID contractId) {
        dsl.deleteFrom(tableName)
                .where(organizationField.eq(organizationId))
                .and(contractField.eq(contractId))
                .execute();
    }

    @Override
    public List<ContractsEntity> getOrganizationContracts(UUID organizationId) {
        return dsl.select(cTable.fields())
                .from(cTable)
                .join(tableName).on(field("contracts.id").eq(contractField))
                .where(organizationField.eq(organizationId))
                .fetch(record -> ContractsEntity.builder()
                        .id(record.get(field("id", UUID.class)))
                        .contractNumber(record.get("contract_number", String.class))
                        .amount(record.get(field("amount", Double.class)))
                        .startDate(record.get(field("start_date", Instant.class)))
                        .endDate(record.get(field("end_date", Instant.class)))
                        .build());
    }
}
