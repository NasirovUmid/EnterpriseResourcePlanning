package com.pm.EnterpriseResourcePlanning.controller;

import com.pm.EnterpriseResourcePlanning.dto.filters.ContractFilterDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ContractsRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ContractResponseDto;
import com.pm.EnterpriseResourcePlanning.usecases.ContractUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractUseCase contractUseCase;

    @PostMapping
    public ResponseEntity<ContractResponseDto> createContract(@Valid @RequestBody ContractsRequestDto contractsRequestDto) {

        ContractResponseDto contractResponseDto = contractUseCase.createContract(contractsRequestDto);

        return ResponseEntity.status(201).body(contractResponseDto);
    }

    @PostMapping("/organizations")
    public ResponseEntity<Void> linkOrganizationContract(@Valid @RequestBody IntermediateRequestDto organizationContractRequestDto) {

        contractUseCase.linkOrganizationContract(organizationContractRequestDto);

        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public Page<ContractResponseDto> getContractsPage(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "startDate,des") String sort,
            @RequestBody ContractFilterDto contractFilterDto
    ) {
        return contractUseCase.getContractPages(page, size, sort, contractFilterDto);
    }

    @GetMapping("/organizations-exist")
    public ResponseEntity<Void> exists(@Valid @RequestBody IntermediateRequestDto organizationContractRequestDto) {

        boolean doesExist = contractUseCase.exists(organizationContractRequestDto);

        return doesExist ? ResponseEntity.ok().build() : ResponseEntity.status(201).build();
    }
    @GetMapping("/organizations/{id}")
    public List<ContractResponseDto> getOrganizationsContracts(@PathVariable(name = "id")UUID id){
        return contractUseCase.getOrganizationsContracts(id);
    }

    @DeleteMapping("/organizations")
    public ResponseEntity<Void> deleteOrganizationContract(@Valid @RequestBody IntermediateRequestDto organizationContractRequestDto) {

        contractUseCase.deleteOrganizationContract(organizationContractRequestDto);

        return ResponseEntity.ok().build();
    }

}
