package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.ProductSalesDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.ProductSalesDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProductResponseDto;
import com.pm.EnterpriseResourcePlanning.mapper.ProductMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProductSalesDataSourceImpl extends MessageAlertDataSource implements ProductSalesDataSource {

    private final ProductMapper productMapper;
    private final ProductSalesDaoImpl productSalesDao;

    public ProductSalesDataSourceImpl(AlertSystemDao alertSystemDao, ProductMapper productMapper, ProductSalesDaoImpl productSalesDao) {
        super(alertSystemDao, ProductSalesDataSourceImpl.class);
        this.productMapper = productMapper;
        this.productSalesDao = productSalesDao;
    }

    @Override
    public void saveProductSales(UUID productId, UUID salesId) {
        execute(() -> productSalesDao.saveProductSales(productId, salesId));
    }

    @Override
    public boolean exists(UUID productId, UUID salesId) {
        return execute(() -> productSalesDao.exists(productId, salesId));
    }

    @Override
    public void removeProductSales(UUID productId, UUID salesId) {
        execute(() -> productSalesDao.removeProductSales(productId, salesId));
    }

    @Override
    public List<ProductResponseDto> getSalesProducts(UUID salesId) {
        return execute(() -> productSalesDao.getSalesProducts(salesId).stream().map(productMapper::toDto).toList());
    }
}
