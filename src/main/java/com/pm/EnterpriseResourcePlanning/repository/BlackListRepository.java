package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.BlackListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BlackListRepository extends JpaRepository<BlackListEntity, UUID> {

    BlackListEntity save(BlackListEntity blackListEntity);

    Optional<BlackListEntity> findById(UUID id);

    void deleteById(UUID id);
}
