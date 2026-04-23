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

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductsEntity, UUID>, JpaSpecificationExecutor<ProductsEntity> {

    @Query(value = """
            INSERT INTO ProductsEntity (name, price, unit,status) VALUES (:name,:price,:unit,:status)""")
    Optional<ProductsEntity> saveProduct(@Param("name") String name, @Param("price") Double price, @Param("unit") Integer unit, @Param("status") ProductStatus status);

    @Transactional(readOnly = true)
    Page<ProductsEntity> findAll(Specification<ProductsEntity> specification, Pageable pageable);

    @Modifying
    @Query(value = """
            UPDATE products SET name = coalesce(:name,name), price = coalesce(:price,price), 
                        unit = coalesce(:unit,unit),status = coalesce(:status,status)""", nativeQuery = true)
    int updateProduct(@Param("name") String name, @Param("price") Double price, @Param("unit") Integer unit,
                      @Param("status") ProductStatus status);

    @Modifying
    @Query(value = """
            UPDATE products SET unit = unit - :amount WHERE id = :id AND unit >= :amount""", nativeQuery = true)
    int updateProductUnit(@Param("amount") Integer amount, @Param("id") UUID id);

    @Query(value = """
            select pe from ProductsEntity pe where pe.id = :id""")
    Optional<ProductsEntity> getProductById(@Param("id") UUID id);

}
