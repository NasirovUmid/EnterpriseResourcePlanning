package com.pm.EnterpriseResourcePlanning.specifications;

import com.pm.EnterpriseResourcePlanning.entity.ProductsEntity;
import com.pm.EnterpriseResourcePlanning.enums.ProductStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductsSpecifications {

    public static Specification<ProductsEntity> build(String name, Double priceGreater, Double priceLower, Integer unitGreater, Integer unitLower, ProductStatus status) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (priceGreater != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), priceGreater));
            }
            if (priceLower != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), priceLower));
            }
            if (unitGreater != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("unit"), unitGreater));
            }
            if (unitLower != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("unit"), unitLower));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.equalTo(status), status));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
