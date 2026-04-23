package com.pm.EnterpriseResourcePlanning.specifications;

import com.pm.EnterpriseResourcePlanning.entity.OrganizationEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSpecifications {

    public static Specification<OrganizationEntity> build(String name, String inn, String address) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name + "%"));
            }
            if (inn != null) {
                predicates.add(criteriaBuilder.equal(root.get("inn"), inn));
            }
            if (address != null) {
                predicates.add(criteriaBuilder.equal(root.get("address"), address));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
