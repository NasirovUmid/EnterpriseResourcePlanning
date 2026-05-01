package com.pm.EnterpriseResourcePlanning.repository;

import com.pm.EnterpriseResourcePlanning.entity.ActionEntity;

import java.util.Optional;

public interface CustomActionRepository {

    Optional<ActionEntity> save(String name);

}
