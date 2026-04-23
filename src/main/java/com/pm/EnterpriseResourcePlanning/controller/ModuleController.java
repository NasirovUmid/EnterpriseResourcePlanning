package com.pm.EnterpriseResourcePlanning.controller;

import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ModuleRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ModuleResponseDto;
import com.pm.EnterpriseResourcePlanning.usecases.ModuleUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/modules")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleUseCase moduleUseCase;

    @PostMapping
    public ResponseEntity<ModuleResponseDto> createModule(@Valid @RequestParam ModuleRequestDto moduleRequestDto) {

        ModuleResponseDto moduleResponseDto = moduleUseCase.createModule(moduleRequestDto);

        return ResponseEntity.status(203).body(moduleResponseDto);
    }

    @GetMapping
    Page<ModuleResponseDto> getModulePages(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size
    ) {

        return moduleUseCase.getModulePages(page, size);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateModule(@PathVariable(name = "id") UUID id, String name) {

        moduleUseCase.updateModule(id, name);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ModuleResponseDto getModuleById(@PathVariable(name = "id")UUID id){

        return moduleUseCase.getModuleById(id);

    }
}
