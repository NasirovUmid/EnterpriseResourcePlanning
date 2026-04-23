package com.pm.EnterpriseResourcePlanning.specifications;

import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ContractSpecification {
    public static Specification<ContractsEntity> build(String contractNumber, Double amountGreater, Double amountLower, Instant startDateFrom, Instant startDateTo, Instant endDateFrom, Instant endDateTo) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (contractNumber != null) {
                predicates.add(criteriaBuilder.like(root.get("contractNumber"), "%" + contractNumber + "%"));
            }
            if (amountGreater != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("amoun"), amountGreater));
            }
            if (amountLower != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("amount"), amountLower));
            }
            if (startDateFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDateFrom));
            }
            if (startDateTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), startDateTo));
            }
            if (endDateFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), endDateFrom));
            }
            if (endDateTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), endDateTo));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
