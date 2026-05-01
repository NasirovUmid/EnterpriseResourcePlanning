package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import com.pm.EnterpriseResourcePlanning.entity.SalesEntity;
import com.pm.EnterpriseResourcePlanning.enums.SalesStatus;
import com.pm.EnterpriseResourcePlanning.repository.CustomSalesRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.jooq.impl.DSL.*;

@Repository
@RequiredArgsConstructor
public class CustomSalesRepositoryImpl implements CustomSalesRepository {

    private final DSLContext dsl;
    private final Table<?> tableName = table("sales");
    private final Field<UUID> idField = field("id", UUID.class);
    private final Field<UUID> contractIdField = field("contract_id", UUID.class);
    private final Field<Double> totalPriceField = field("total_price", Double.class);
    private final Field<Instant> dateField = field("date", Instant.class);
    private final Field<Object> statusField = field("status", DSL.name("sales_status"));


    @Override
    public Optional<SalesEntity> saveSales(UUID contractId, Double totalprice, Instant date, SalesStatus status) {

        var record = dsl.insertInto(tableName)
                .columns(contractIdField, totalPriceField, dateField, statusField)
                .values(contractId, totalprice, date, DSL.field("?::sales_status", status))
                .returning(idField, contractIdField, totalPriceField, dateField, statusField)
                .fetchOne();

        if (record == null) {
            return Optional.empty();
        }

        return Optional.of(SalesEntity.builder()
                .id(record.get(idField))
                .contractId(record.get(contractIdField))
                .totalPrice(record.get(totalPriceField))
                .date(record.get(dateField))
                .status(SalesStatus.valueOf(String.valueOf(record.get(statusField))))
                .build());
    }
}
