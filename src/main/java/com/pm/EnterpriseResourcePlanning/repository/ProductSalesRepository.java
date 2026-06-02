package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductSalesRepository extends JpaRepository<ProductsEntity, UUID>, CustomProductSalesRepository {

    List<ProductsEntity> findAllById(Iterable<UUID> ids);

}
