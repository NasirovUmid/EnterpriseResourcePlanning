package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.ProjectEntity;
import com.pm.EnterpriseResourcePlanning.entity.UserEntity;
import com.pm.EnterpriseResourcePlanning.enums.ProjectStatus;
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
public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID>, JpaSpecificationExecutor<ProjectEntity>, CustomProjectRepository {

    @Query(value = """
            SELECT u.* FROM users u
            JOIN user_organization uo ON u.id = uo.user_id
            JOIN project_organization po ON uo.organization_id = po.organization_id
            WHERE po.project_id = :projectId
            """, nativeQuery = true)
    List<UserEntity> findUsersByProject(@Param("projectId") UUID projectId);

    @Transactional(readOnly = true)
    Page<ProjectEntity> findAll(Specification<ProjectEntity> specification, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE projects SET name = coalesce(:name,name),status = coalesce(:status,status) where id = :id""", nativeQuery = true)
    void updateProject(@Param("name") String name, @Param("status") ProjectStatus status, @Param("id") UUID id);

    @Transactional(readOnly = true)
    @Query(value = """
            select pe from ProjectEntity pe where pe.id = :id""")
    Optional<ProjectEntity> getProjectById(@Param("id") UUID id);
}
