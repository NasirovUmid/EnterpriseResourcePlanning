package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.ProductsEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomProductSalesRepository {

    void saveProductSales(UUID productId, UUID salesId, Integer quantity, Double price);

    boolean exists(UUID productId, UUID salesId);

    void removeProductSales(UUID productId, UUID salesId);

    List<ProductsEntity> getSalesProducts(UUID salesId);

}
