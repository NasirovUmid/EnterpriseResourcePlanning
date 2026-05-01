package com.pm.EnterpriseResourcePlanning.datasource.impl;

import com.pm.EnterpriseResourcePlanning.dao.AlertSystemDao;
import com.pm.EnterpriseResourcePlanning.dao.impl.ProductDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.ProductDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.MessageAlertDataSource;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProductResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ProductsEntity;
import com.pm.EnterpriseResourcePlanning.enums.ProductStatus;
import com.pm.EnterpriseResourcePlanning.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductDataSourceImpl extends MessageAlertDataSource implements ProductDataSource {

    private final ProductDaoImpl productDao;
    private final ProductMapper productMapper;

    public ProductDataSourceImpl(AlertSystemDao alertSystemDao, ProductDaoImpl productDao, ProductMapper productMapper) {
        super(alertSystemDao, ProductDataSourceImpl.class);
        this.productDao = productDao;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponseDto saveProduct(String name, Double price, Integer unit, ProductStatus status) {
        return execute(() -> productMapper.toDto(productDao.saveProduct(name, price, unit, status)));
    }

    @Override
    public Page<ProductResponseDto> getProductsPage(Pageable pageable, Specification<ProductsEntity> specification) {
        return execute(() -> productDao.getProductsPage(pageable, specification).map(productMapper::toDto));
    }

    @Override
    public void updateProduct(String name, Double price, Integer unit, ProductStatus status,UUID id) {
        execute(() -> productDao.updateProduct(name, price, unit, status,id));
    }

    @Override
    public void updateProductUnit(Integer amount, UUID id) {
        execute(() -> productDao.updateProductUnit(amount, id));
    }

    @Override
    public ProductResponseDto getProductById(UUID id) {
        return execute(() -> productMapper.toDto(productDao.getProductById(id)));
    }
}
