package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.ProductsEntity;
import com.pm.EnterpriseResourcePlanning.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductsEntity, UUID>, JpaSpecificationExecutor<ProductsEntity>, CustomProductRepository {

    @Transactional(readOnly = true)
    Page<ProductsEntity> findAll(Specification<ProductsEntity> specification, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE ProductsEntity pe SET pe.name = coalesce(:name,pe.name), pe.price = coalesce(:price,pe.price), 
                        pe.unit = coalesce(:unit,pe.unit),pe.status = coalesce(:status,pe.status) where pe.id = :id""")
    int updateProduct(@Param("name") String name, @Param("price") Double price, @Param("unit") Integer unit,
                      @Param("status") ProductStatus status, @Param("id") UUID id);

    @Modifying
    @Query(value = """
            UPDATE products SET unit = unit - :amount WHERE id = :id AND unit >= :amount""", nativeQuery = true)
    void updateProductUnit(@Param("amount") Integer amount, @Param("id") UUID id);

    @Transactional(readOnly = true)
    Optional<ProductsEntity> findProductsEntityById(UUID id);

}
