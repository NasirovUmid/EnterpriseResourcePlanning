package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProductResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ProductsEntity;
import com.pm.EnterpriseResourcePlanning.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface ProductDataSource {

    ProductResponseDto saveProduct(String name, Double price, Integer unit, ProductStatus status);

    Page<ProductResponseDto> getProductsPage(Pageable pageable, Specification<ProductsEntity> specification);

    void updateProduct(String name, Double price, Integer unit, ProductStatus status,UUID id);

    void updateProductUnit(Integer amount, UUID id);

    ProductResponseDto getProductById(UUID id);

}
