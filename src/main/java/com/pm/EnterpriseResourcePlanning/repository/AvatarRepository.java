package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.AvatarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AvatarRepository extends JpaRepository<AvatarEntity, UUID>, AvatarCustomRepository {

    @Modifying
    @Query(value = """
            UPDATE avatars SET url = :url WHERE id = :id""",nativeQuery = true)
    void updateAvatar(@Param("url") String url, @Param("id") UUID id);


}
