package com.pm.EnterpriseResourcePlanning.datasource;

import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ActionResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ActionDataSource {

    ActionResponseDto saveAction(String name);

    Page<ActionResponseDto> getActionPage(Pageable pageable);

    void updateAction(String name, UUID id);

    ActionResponseDto getActionById(UUID id);


}
