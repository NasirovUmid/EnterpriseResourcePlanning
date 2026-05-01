package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.ModuleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleEntity, UUID>,CustomModuleRepository {

    @Transactional(readOnly = true)
    Page<ModuleEntity> findAll(Pageable pageable);

    @Modifying
    @Query(value = """
            UPDATE modules set name = :name  where id = :id""", nativeQuery = true)
    int updateModule(@Param("name") String name, @Param("id") UUID id);

    @Query(value = """
            SELECT me FROM ModuleEntity me where me.id = :id""")
    Optional<ModuleEntity> getModuleById(@Param("id") UUID id);
}
