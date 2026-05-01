package com.pm.EnterpriseResourcePlanning.controller;

import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ActionRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ActionResponseDto;
import com.pm.EnterpriseResourcePlanning.usecases.ActionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/actions")
@RequiredArgsConstructor
public class ActionController {

    private final ActionUseCase actionUseCase;

    @PostMapping
    public ResponseEntity<ActionResponseDto> createAction(@RequestBody ActionRequestDto actionRequestDto) {

        ActionResponseDto actionResponseDto = actionUseCase.createAction(actionRequestDto);

        return ResponseEntity.status(201).body(actionResponseDto);
    }

    @GetMapping
    List<ActionResponseDto> getActionPages() {
        return actionUseCase.getActionPages();
    }

    @GetMapping("/{id}")
    public ActionResponseDto getActionById(@PathVariable(name = "id") UUID id) {
        return actionUseCase.getActionById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAction(@PathVariable(name = "id") UUID id, String name) {

        actionUseCase.updateAction(id, name);

        return ResponseEntity.ok().build();
    }
}
