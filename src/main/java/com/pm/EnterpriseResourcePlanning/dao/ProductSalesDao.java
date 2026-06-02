package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.ProductsEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ProductSalesDao {

    void saveProductSales(UUID productId, UUID salesId, Integer quantity, Double price);

    boolean exists(UUID productId, UUID salesId);

    void removeProductSales(UUID productId, UUID salesId);

    List<ProductsEntity> getSalesProducts(UUID salesId);

    List<ProductsEntity> findAll(Set<UUID> idS);

}
