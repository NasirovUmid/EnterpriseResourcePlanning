package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.datasource.ActionDataSource;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ActionRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ActionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActionUseCase {

    private final ActionDataSource actionDataSource;

    @Transactional
    public ActionResponseDto createAction(ActionRequestDto actionRequestDto) {
        return actionDataSource.saveAction(actionRequestDto.name());
    }

    @Transactional(readOnly = true)
    public List<ActionResponseDto> getActionPages() {
        return actionDataSource.getActionPage();
    }

    public ActionResponseDto getActionById(UUID id) {
        return actionDataSource.getActionById(id);
    }

    @Transactional
    public void updateAction(UUID id, String name) {
        actionDataSource.updateAction(name, id);
    }
}
