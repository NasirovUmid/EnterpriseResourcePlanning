package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.ProductsEntity;

import java.util.List;
import java.util.UUID;

public interface ProductSalesRepository {

    void saveProductSales(UUID productId, UUID salesId);

    boolean exists(UUID productId, UUID salesId);

    void removeProductSales(UUID productId, UUID salesId);

    List<ProductsEntity> getSalesProducts(UUID salesId);
}
