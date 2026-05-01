package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.ProductsEntity;
import com.pm.EnterpriseResourcePlanning.enums.ProductStatus;
import com.pm.EnterpriseResourcePlanning.repository.CustomProductRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static org.jooq.impl.DSL.*;

@Repository
@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {

    private final DSLContext dsl;
    private final Table<?> tableName = table("products");
    private final Field<String> nameField = field("name", String.class);
    private final Field<Double> priceField = field("price", Double.class);
    private final Field<Integer> unitField = field("unit", Integer.class);
    private final Field<UUID> idField = field("id", UUID.class);
    private final Field<Object> statusField = field("status", DSL.name("product_status"));


    @Override
    public Optional<ProductsEntity> saveProduct(String name, Double price, Integer unit, ProductStatus status) {

        if (status == null) {
            status = ProductStatus.ACTIVE;
        }

        var record = dsl.insertInto(tableName)
                .columns(nameField, priceField, unitField, statusField)
                .values(name, price, unit, DSL.field("?::product_status", status.name()))
                .returning(idField, nameField, priceField, unitField, statusField)
                .fetchOne();

        if (record == null) {
            return Optional.empty();
        }

        return Optional.of(ProductsEntity.builder()
                .id(record.get(idField))
                .name(record.get(nameField))
                .price(record.get(priceField))
                .unit(record.get(unitField))
                .status(ProductStatus.valueOf(String.valueOf(record.get(statusField))))
                .build());
    }
}
