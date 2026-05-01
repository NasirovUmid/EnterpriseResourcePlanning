package com.pm.EnterpriseResourcePlanning.dao.impl;

import com.pm.EnterpriseResourcePlanning.dao.ProductDao;
import com.pm.EnterpriseResourcePlanning.entity.ProductsEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.enums.ProductStatus;
import com.pm.EnterpriseResourcePlanning.exceptions.NotFoundException;
import com.pm.EnterpriseResourcePlanning.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductDaoImpl implements ProductDao {

    private final ProductRepository repository;

    @Override
    public ProductsEntity saveProduct(String name, Double price, Integer unit, ProductStatus status) {
        return repository.saveProduct(name, price, unit, status).orElseThrow(RuntimeException::new);
    }

    @Override
    public Page<ProductsEntity> getProductsPage(Pageable pageable, Specification<ProductsEntity> specification) {
        return repository.findAll(specification, pageable);
    }

    @Override
    public void updateProduct(String name, Double price, Integer unit, ProductStatus status, UUID id) {
        repository.updateProduct(name, price, unit, status, id);
    }

    @Override
    public void updateProductUnit(Integer amount, UUID id) {
        repository.updateProductUnit(amount, id);
    }

    @Override
    public ProductsEntity getProductById(UUID id) {
        return repository.findProductsEntityById(id).orElseThrow(() -> new NotFoundException(ErrorMessages.PRODUCT_NOT_FOUND, id));
    }
}
