package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.ActionEntity;
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
public interface ActionRepository extends JpaRepository<ActionEntity, UUID>,CustomActionRepository {



    @Transactional(readOnly = true)
    Page<ActionEntity> findAll(Pageable pageable);

    @Modifying
    @Query(value = """
            UPDATE actions SET name = :name where id = :id""", nativeQuery = true)
    int updateAction(@Param("name") String name, @Param("id") UUID id);

    @Query(value = """
            select ae from ActionEntity ae where ae.id = :id""")
    Optional<ActionEntity> getActionById(@Param("id") UUID id);
}
