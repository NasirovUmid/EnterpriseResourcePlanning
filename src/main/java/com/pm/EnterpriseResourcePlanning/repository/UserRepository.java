package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.UserEntity;
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
public interface UserRepository extends JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity>, CustomUserRepository {

    @Modifying
    @Transactional
    @Query(value = """
            update users set full_name = coalesce(:fullname,full_name) , phone_number = :phonenumber where id = :id
            """, nativeQuery = true)
    void updateUser(@Param("id") UUID id,
                    @Param("fullname") String fullName,
                    @Param("phonenumber")String phoneNumber);

    @Modifying
    @Query(value = """
            update users set status = 'DEACTIVATED' where id =:id""", nativeQuery = true)
    void deactivateUser(@Param("id") UUID uuid);

    @Transactional(readOnly = true)
    Page<UserEntity> findAll(Specification<UserEntity> specification, Pageable pageable);

    Optional<UserEntity> getUserEntityById(UUID id);

    boolean existsByUsername(String username);

    Optional<UserEntity> findUserEntityByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = """
            update users set password = :password where id = :id""", nativeQuery = true)
    void updateUserPassword(@Param("id") UUID id, @Param("password") String password);
}
