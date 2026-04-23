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

//    @Query(value = """
//            insert into UserEntity (fullName,username,password,phoneNumber,avatar)
//                        VALUES (:fullname,:username,:password,:phone,:avatar)""")
//    Optional<UserEntity> saveUser(@Param("fullname") String fullName, @Param("username") String name,
//                                  @Param("password") String password, @Param("avatar") AvatarEntity avatarId, @Param("phone") String phone);

    @Query(value = """
            
            SELECT EXISTS (
                SELECT 1 FROM user_roles ur
                JOIN role_permissions rp ON ur.role_id = rp.role_id
                JOIN permissions p ON rp.permission_id = p.id
                JOIN modules m ON p.module_id = m.id
                JOIN actions a ON p.action_id = a.id
                WHERE ur.user_id = :userId\s
                  AND m.name = :moduleName\s
                  AND a.name = :actionName
            
            )""", nativeQuery = true)
    boolean checkAccess(@Param("userId") UUID userId,
                        @Param("moduleName") String moduleName,
                        @Param("actionName") String actionName);

    @Modifying
    @Transactional
    @Query(value = """
            update users set full_name = coalesce(:fullname,full_name),
                                         password = coalesce(:password,password),
                                                     avatar_id = coalesce(:avatarId,avatar_id)""", nativeQuery = true)
    void updateUser(@Param("id") UUID id,
                    @Param("fullname") String fullName,
                    @Param("password") String password,
                    @Param("avatarId") UUID avatarId);

    @Modifying
    @Query(value = """
            update users set status = 'DEACTIVATED',updated_at = now() , updated_by = 'f025da77-d7ac-4fdf-b84f-ce57213d584a' where id =:id""", nativeQuery = true)
    void deactivateUser(@Param("id") UUID uuid);

    @Transactional(readOnly = true)
    Page<UserEntity> findAll(Specification<UserEntity> specification, Pageable pageable);

    @Query(value = """
            SELECT ue FROM UserEntity ue where ue.id = :id""")
    Optional<UserEntity> getUserById(@Param("id") UUID id);

    boolean existsByUsername(String username);

    Optional<UserEntity> findUserEntityByUsername(String username);
}
