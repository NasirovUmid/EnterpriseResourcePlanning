package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import com.pm.EnterpriseResourcePlanning.repository.ContractClientsRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static org.jooq.impl.DSL.*;

@Repository
@RequiredArgsConstructor
public class ContractClientsRepositoryImpl implements ContractClientsRepository {

    private final DSLContext dsl;
    private final Table<?> tableName = table("contract_clients");
    private final Field<UUID> contractField = field("contract_id", UUID.class);
    private final Field<UUID> clientsField = field("client_id", UUID.class);
    private final Table<?> cTable = table("contracts");

    @Override
    public void saveContractClient(UUID contractId, UUID clientId, Integer ownershipShare) {
        dsl.insertInto(tableName)
                .columns(contractField, clientsField, field("ownership_share", Integer.class))
                .values(contractId, clientId, ownershipShare)
                .execute();
    }

    @Override
    public boolean exists(UUID contractId, UUID clientId) {
        return dsl.fetchExists(dsl.selectOne()
                .from(tableName)
                .where(contractField.eq(contractId))
                .and(clientsField.eq(clientId)));
    }

    @Override
    public void removeContractClient(UUID contractId, UUID clientId) {
        dsl.deleteFrom(tableName)
                .where(contractField.eq(contractId))
                .and(clientsField.eq(clientId))
                .execute();
    }

    @Override
    public List<ContractsEntity> getClientContracts(UUID clientId) {
        return dsl.select(cTable.fields())
                .from(cTable)
                .join(tableName).on(field("contracts.id").eq(tableName.field(contractField)))
                .where(clientsField.eq(clientId))
                .fetchInto(ContractsEntity.class);
    }
}
