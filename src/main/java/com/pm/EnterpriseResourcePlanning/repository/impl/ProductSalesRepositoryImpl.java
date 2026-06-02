package com.pm.EnterpriseResourcePlanning.repository.impl;

import com.pm.EnterpriseResourcePlanning.entity.ProductsEntity;
import com.pm.EnterpriseResourcePlanning.enums.ProductStatus;
import com.pm.EnterpriseResourcePlanning.repository.CustomProductSalesRepository;
import com.pm.EnterpriseResourcePlanning.repository.ProductSalesRepository;
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
public class ProductSalesRepositoryImpl implements CustomProductSalesRepository {

    private final DSLContext dsl;
    private final Table<?> tableName = table("product_sales");
    private final Table<?> pTable = table("products");
    private final Field<UUID> productField = field("product_id", UUID.class);
    private final Field<UUID> salesField = field("sales_id", UUID.class);
    private final Field<Integer> quantityField = field("quantity", Integer.class);
    private final Field<Double> priceField = field("total_price", Double.class);

    @Transactional
    @Override
    public void saveProductSales(UUID productId, UUID salesId, Integer quantity, Double price) {
        dsl.insertInto(tableName)
                .columns(productField, salesField, quantityField, priceField)
                .values(productId, salesId,quantity,price)
                .execute();
    }

    @Override
    public boolean exists(UUID productId, UUID salesId) {
        return dsl.fetchExists(dsl.selectOne()
                .from(tableName)
                .where(productField.eq(productId))
                .and(salesField.eq(salesId)));
    }

    @Transactional
    @Override
    public void removeProductSales(UUID productId, UUID salesId) {
        dsl.deleteFrom(tableName)
                .where(productField.eq(productId))
                .and(salesField.eq(salesId))
                .execute();
    }

    @Override
    public List<ProductsEntity> getSalesProducts(UUID salesId) {
        return dsl.select(pTable.fields())
                .from(pTable)
                .join(tableName).on(field("products.id").eq(tableName.field(productField)))
                .fetch(record -> ProductsEntity.builder()
                        .id(record.get("id", UUID.class))
                        .name(record.get("name", String.class))
                        .price(record.get("price", Double.class))
                        .unit(record.get("unit", Integer.class))
                        .status(ProductStatus.valueOf(record.get("status", String.class)))
                        .build());
    }
}
