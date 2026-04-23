package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.ProductsEntity;
import com.pm.EnterpriseResourcePlanning.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface ProductDao {

    ProductsEntity saveProduct(String name, Double price, Integer unit, ProductStatus status);

    Page<ProductsEntity> getProductsPage(Pageable pageable, Specification<ProductsEntity> specification);

    void updateProduct(String name, Double price, Integer unit, ProductStatus status);

    void updateProductUnit(Integer amount, UUID id);

    ProductsEntity getProductById(UUID id);

}
