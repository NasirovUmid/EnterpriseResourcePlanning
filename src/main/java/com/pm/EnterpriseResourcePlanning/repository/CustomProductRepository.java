package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.ProductsEntity;
import com.pm.EnterpriseResourcePlanning.enums.ProductStatus;

import java.util.Optional;

public interface CustomProductRepository {

    Optional<ProductsEntity> saveProduct(String name, Double price, Integer unit, ProductStatus status);

}
