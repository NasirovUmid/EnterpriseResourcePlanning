package com.pm.EnterpriseResourcePlanning.controller;

import com.pm.EnterpriseResourcePlanning.dto.filters.ContractFilterDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ContractUpdateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ContractsRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ContractResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProjectResponseDto;
import com.pm.EnterpriseResourcePlanning.usecases.ContractUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractUseCase contractUseCase;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MOD_CONTRACTS')")
    public ResponseEntity<ContractResponseDto> createContract(@Valid @RequestBody ContractsRequestDto contractsRequestDto) {

        ContractResponseDto contractResponseDto = contractUseCase.createContract(contractsRequestDto);

        return ResponseEntity.status(201).body(contractResponseDto);
    }

    @PostMapping("/organizations")
    @PreAuthorize("hasAnyRole('ADMIN','MOD_CONTRACTS')")
    public ResponseEntity<Void> linkOrganizationContract(@Valid @RequestBody IntermediateRequestDto organizationContractRequestDto) {

        contractUseCase.linkOrganizationContract(organizationContractRequestDto);

        return ResponseEntity.status(201).build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MOD_CONTRACTS')")
    public Page<ContractResponseDto> getContractsPage(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "startDate,des") String sort,
            @Valid @ModelAttribute ContractFilterDto contractFilterDto
    ) {
        return contractUseCase.getContractPages(page, size, sort, contractFilterDto);
    }

    @GetMapping("/organizations/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MOD_CONTRACTS','MOD_ORGANIZATIONS')")
    public List<ContractResponseDto> getOrganizationsContracts(@PathVariable(name = "id") UUID id) {
        return contractUseCase.getOrganizationsContracts(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MOD_CONTRACTS')")
    public ContractResponseDto getContractById(@PathVariable(name = "id") UUID id) {
        return contractUseCase.getContractById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MOD_CONTRACTS')")
    public ResponseEntity<Void> updateContract(@PathVariable(name = "id") UUID id,
                                               @Valid @RequestBody ContractUpdateRequestDto requestDto) {
        contractUseCase.updateContract(id, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteContract(@PathVariable(name = "id") UUID id) {
        contractUseCase.deleteContract(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MOD_CONTRACTS')")
    @DeleteMapping("/organizations")
    public ResponseEntity<Void> deleteOrganizationContract(@Valid @RequestBody IntermediateRequestDto organizationContractRequestDto) {

        contractUseCase.deleteOrganizationContract(organizationContractRequestDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/projects")
    @PreAuthorize("hasAnyRole('ADMIN','MOD_CONTRACTS')")
    public ResponseEntity<Void> saveContractProject(@Valid @RequestBody IntermediateRequestDto requestDto) {
        contractUseCase.saveContractProject(requestDto);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/projects")
    @PreAuthorize("hasAnyRole('ADMIN','MOD_CONTRACTS')")
    public ResponseEntity<Void> deleteContractProject(@Valid @RequestBody IntermediateRequestDto requestDto) {
        contractUseCase.deleteContractProject(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/projects/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MOD_CONTRACTS','MOD_PROJECTS')")
    public List<ContractResponseDto> getContractsByProjectId(@PathVariable(name = "id") UUID id) {
        return contractUseCase.getContractsByProjectId(id);
    }

    @GetMapping("/contracts/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MOD_CONTRACTS')")
    public List<ProjectResponseDto> getProjectsByContractId(@PathVariable(name = "id") UUID id) {
        return contractUseCase.getProjectsByContractId(id);
    }

}
