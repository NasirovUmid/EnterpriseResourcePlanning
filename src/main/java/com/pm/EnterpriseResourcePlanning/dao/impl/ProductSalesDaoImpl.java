package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.ProductSalesDao;
import com.pm.EnterpriseResourcePlanning.entity.ProductsEntity;
import com.pm.EnterpriseResourcePlanning.repository.ProductSalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductSalesDaoImpl implements ProductSalesDao {

    private final ProductSalesRepository repository;

    @Override
    public void saveProductSales(UUID productId, UUID salesId) {
        repository.saveProductSales(productId, salesId);
    }

    @Override
    public boolean exists(UUID productId, UUID salesId) {
        return repository.exists(productId, salesId);
    }

    @Override
    public void removeProductSales(UUID productId, UUID salesId) {
        repository.removeProductSales(productId, salesId);
    }

    @Override
    public List<ProductsEntity> getSalesProducts(UUID salesId) {
        return repository.getSalesProducts(salesId);
    }
}
