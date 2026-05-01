package com.pm.EnterpriseResourcePlanning.controller;

import com.pm.EnterpriseResourcePlanning.dto.filters.ClientFilterDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ClientRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ContractClientRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ClientResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ContractResponseDto;
import com.pm.EnterpriseResourcePlanning.usecases.ClientUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientUseCase clientUseCase;

    @PostMapping
    public ResponseEntity<ClientResponseDto> createClient(@Valid @RequestBody ClientRequestDto clientRequestDto) {

        ClientResponseDto clientResponseDto = clientUseCase.createClient(clientRequestDto);

        return ResponseEntity.status(201).body(clientResponseDto);
    }

    @GetMapping
    public Page<ClientResponseDto> getClientsPage(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "fullname") String sort,
            @Valid @ModelAttribute ClientFilterDto clientFilterDto

    ) {
        return clientUseCase.getClientsPage(page, size, sort, clientFilterDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDto> getClient(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok(clientUseCase.getClient(id));
    }

    @PostMapping("/contracts")
    public ResponseEntity<Void> saveContractClient(@Valid @RequestBody ContractClientRequestDto requestDto) {

        clientUseCase.saveContractClient(requestDto);

        return ResponseEntity.ok().build();
    }


    @PreAuthorize("hasAnyRole('ADMIN','MOD_ORGANIZATIONS')")
    @DeleteMapping("/contracts")
    public ResponseEntity<Void> removeContractClient(@Valid @RequestBody IntermediateRequestDto requestDto) {

        clientUseCase.removeContractClient(requestDto);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/contracts/{id}")
    public List<ContractResponseDto> getClientContracts(@PathVariable(name = "id") UUID id) {
        return clientUseCase.getClientContracts(id);
    }
}
