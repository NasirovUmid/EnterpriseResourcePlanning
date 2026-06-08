package com.pm.EnterpriseResourcePlanning.specifications;

import com.pm.EnterpriseResourcePlanning.dto.filters.SalesFilterDto;
import com.pm.EnterpriseResourcePlanning.entity.SalesEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SalesSpecification {
    public static Specification<SalesEntity> build(SalesFilterDto salesFilterDto) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (salesFilterDto.greaterThan() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("totalPrice"), salesFilterDto.greaterThan()));
            }

            if (salesFilterDto.lowerThan() != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("totalPrice"),salesFilterDto.lowerThan()));
            }

            if (salesFilterDto.date() != null){
                predicates.add(criteriaBuilder.equal(root.get("date"),salesFilterDto.date()));
            }

            if (salesFilterDto.status() != null){
                predicates.add(criteriaBuilder.equal(root.get("status"),salesFilterDto.status()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    }
}
