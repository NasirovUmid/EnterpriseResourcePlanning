package com.pm.EnterpriseResourcePlanning.specifications;

import com.pm.EnterpriseResourcePlanning.entity.UserEntity;
import com.pm.EnterpriseResourcePlanning.enums.UserStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecifications {

    public static Specification<UserEntity> build(String fullName, String username, String phoneNumber, UserStatus userStatus) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (fullName != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + fullName.toLowerCase() + "%"));
            }
            if (username != null) {
                predicates.add(criteriaBuilder.equal(root.get("username"), username));
            }
            if (phoneNumber != null) {
                predicates.add(criteriaBuilder.like(root.get("phoneNumber"), "%" + phoneNumber + "%"));
            }
            if ((userStatus != null)) {
                predicates.add(criteriaBuilder.equal(root.get("userStatus"), userStatus));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
