package com.pm.EnterpriseResourcePlanning.dao;

import com.pm.EnterpriseResourcePlanning.entity.ActionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ActionDao {

    ActionEntity saveAction(String name);

    Page<ActionEntity> getActionPage(Pageable pageable);

    void updateAction(String name, UUID id);

    ActionEntity getActionById(UUID id);

}
