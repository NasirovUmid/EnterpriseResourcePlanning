package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.AvatarEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface AvatarRepository extends JpaRepository<AvatarEntity, UUID>, AvatarCustomRepository {

    @Transactional(readOnly = true)
    Page<AvatarEntity> findAll(Pageable pageable);

    @Modifying
    @Query(value = """
            UPDATE avatars SET url = :url WHERE id = :id""",nativeQuery = true)
    void updateAvatar(@Param("url") String url, @Param("id") UUID id);
}
