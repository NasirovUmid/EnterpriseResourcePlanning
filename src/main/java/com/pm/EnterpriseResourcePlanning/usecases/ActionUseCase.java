package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.datasource.impl.ActionDataSourceImpl;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ActionRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ActionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActionUseCase {

    private final ActionDataSourceImpl actionDataSource;

    public ActionResponseDto createAction(ActionRequestDto actionRequestDto) {
        return actionDataSource.saveAction(actionRequestDto.name());
    }

    public Page<ActionResponseDto> getActionPages(int page, int size) {
        return actionDataSource.getActionPage(PageRequest.of(page, size));
    }

    public ActionResponseDto getActionById(UUID id) {
        return actionDataSource.getActionById(id);
    }

    public void updateAction(UUID id, String name) {
        actionDataSource.updateAction(name, id);
    }
}
