package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProductResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ProductsEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ProductSalesDataSource {

    void saveProductSales(UUID productId, UUID salesId, Integer quantity, Double price);

    boolean exists(UUID productId, UUID salesId);

    void removeProductSales(UUID productId, UUID salesId);

    List<ProductResponseDto> getSalesProducts(UUID salesId);

    List<ProductsEntity> findAllByIds(Set<UUID> ids);

}
