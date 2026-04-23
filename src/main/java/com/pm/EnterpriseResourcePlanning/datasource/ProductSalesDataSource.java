package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProductResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProductSalesDataSource {

    void saveProductSales(UUID productId, UUID salesId);

    boolean exists(UUID productId, UUID salesId);

    void removeProductSales(UUID productId, UUID salesId);

    List<ProductResponseDto> getSalesProducts(UUID salesId);

}
