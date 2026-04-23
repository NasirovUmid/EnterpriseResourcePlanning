package com.pm.EnterpriseResourcePlanning.specifications;

import com.pm.EnterpriseResourcePlanning.entity.ClientEntity;
import com.pm.EnterpriseResourcePlanning.enums.ClientType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ClientSpecification {
    public static Specification<ClientEntity> build(String fullname, String phone, ClientType type) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (fullname != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fullname")), "%" + fullname + "%"));
            }
            if (phone != null) {
                predicates.add(criteriaBuilder.like(root.get("phone"), phone));
            }
            if (type != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    }
}
