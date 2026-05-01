package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import com.pm.EnterpriseResourcePlanning.repository.CustomContractRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.jooq.impl.DSL.*;


@Repository
@RequiredArgsConstructor
public class CustomContractRepositoryImpl implements CustomContractRepository {

    private final DSLContext dsl;
    private final Table<?> tableName = table("contracts");
    private final Field<String> numberField = field("contract_number", String.class);
    private final Field<Double> amountField = field("amount", Double.class);
    private final Field<Instant> startField = field("start_date", Instant.class);
    private final Field<Instant> endField = field("end_date", Instant.class);
    private final Field<UUID> idField = field("id", UUID.class);

    @Override
    public Optional<ContractsEntity> saveContract(String name, Double amount, Instant startDate, Instant endDate) {

        var record = dsl.insertInto(tableName)
                .columns(numberField, amountField, startField, endField)
                .values(name, amount, startDate, endDate)
                .returning(idField, numberField, amountField, startField, endField)
                .fetchOne();

        if (record == null) {
            return Optional.empty();
        }

        return Optional.of(ContractsEntity.builder()
                .id(record.get(idField))
                .contractNumber(record.get(numberField))
                .amount(record.get(amountField))
                .startDate(record.get(startField))
                .endDate(record.get(endField))
                .build());
    }
}
