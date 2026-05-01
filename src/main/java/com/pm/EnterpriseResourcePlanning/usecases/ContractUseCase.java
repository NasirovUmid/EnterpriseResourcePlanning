package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.datasource.ContractDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.ContractProjectDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.OrganizationContractDataSource;
import com.pm.EnterpriseResourcePlanning.dto.filters.ContractFilterDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ContractUpdateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ContractsRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ContractResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProjectResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.exceptions.AlreadyExistsException;
import com.pm.EnterpriseResourcePlanning.exceptions.MethodArgumentNotValidException;
import com.pm.EnterpriseResourcePlanning.specifications.ContractSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractUseCase {

    private final ContractDataSource contractDataSource;
    private final OrganizationContractDataSource organizationContractDataSource;
    private final ContractProjectDataSource contractProjectDataSource;

    @Transactional
    public ContractResponseDto createContract(@Valid ContractsRequestDto contractsRequestDto) {

        if (contractDataSource.existsByContractNumber(contractsRequestDto.contractNumber())) {
            throw new AlreadyExistsException(ErrorMessages.USER_ALREADY_EXISTS, contractsRequestDto.contractNumber());
        }

        if (contractsRequestDto.startDate().isAfter(contractsRequestDto.endDate()) || contractsRequestDto.startDate().equals(contractsRequestDto.endDate())) {
            throw new MethodArgumentNotValidException(ErrorMessages.INVALID_DATES);
        }
        return contractDataSource.saveContract(contractsRequestDto.contractNumber(), contractsRequestDto.amount(), contractsRequestDto.startDate(), contractsRequestDto.endDate());
    }

    @Transactional(readOnly = true)
    public Page<ContractResponseDto> getContractPages(int page, int size, String sort, ContractFilterDto filterDto) {

        Specification<ContractsEntity> specification =
                ContractSpecification.build(filterDto.contractNumber(), filterDto.amountGreater(), filterDto.amountLower(),
                        filterDto.startDateFrom(), filterDto.startDateTo(), filterDto.endDateFrom(), filterDto.endDateTo());

        Pageable pageable = PageRequest.of(page, size, toContractEntitySort(sort));

        return contractDataSource.getContractsPage(specification, pageable);
    }

    @Transactional
    public void linkOrganizationContract(@Valid IntermediateRequestDto organizationContractRequestDto) {

        if (organizationContractDataSource.exists(organizationContractRequestDto.uuid(), organizationContractRequestDto.uuid1())) {
            throw new AlreadyExistsException(ErrorMessages.ORGANIZATION_CONTRACT_ALREADY_EXISTS, organizationContractRequestDto.uuid(), organizationContractRequestDto.uuid1());
        }
        organizationContractDataSource.linkOrganizationContract(organizationContractRequestDto.uuid(), organizationContractRequestDto.uuid1());
    }

    public boolean exists(@Valid IntermediateRequestDto organizationContractRequestDto) {
        return organizationContractDataSource.exists(organizationContractRequestDto.uuid(), organizationContractRequestDto.uuid1());
    }

    @Transactional
    public void deleteOrganizationContract(IntermediateRequestDto organizationContractRequestDto) {
        organizationContractDataSource.removeOrganizationContractLink(organizationContractRequestDto.uuid(), organizationContractRequestDto.uuid1());
    }

    @Transactional(readOnly = true)
    public List<ContractResponseDto> getOrganizationsContracts(UUID id) {
        return organizationContractDataSource.getOrganizationContracts(id);
    }

    @Transactional(readOnly = true)
    public ContractResponseDto getContractById(UUID id) {
        return contractDataSource.getContractById(id);
    }

    @Transactional
    public void updateContract(UUID id, ContractUpdateRequestDto requestDto) {
        if (requestDto.startDate() != null && requestDto.endDate() != null &&
                (requestDto.startDate().isAfter(requestDto.endDate()) || requestDto.startDate().equals(requestDto.endDate()))) {
            throw new MethodArgumentNotValidException(ErrorMessages.INVALID_DATES);
        }

        contractDataSource.updateContracts(requestDto.amount(), requestDto.startDate(), requestDto.endDate(), id);
    }

    @Transactional
    public void deleteContract(UUID id) {
        contractDataSource.deleteContract(id);
    }

    private Sort toContractEntitySort(String sort) {

        if (sort == null || !sort.contains(",")) {
            return Sort.by("startDate,desc");
        }

        String[] parts = sort.split(",");
        String field = parts[0];
        String direction = parts[1];

        return direction.equalsIgnoreCase("desc") ?
                Sort.by(field).descending() :
                Sort.by(field).ascending();
    }

    @Transactional
    public void saveContractProject(@Valid IntermediateRequestDto requestDto) {
        contractProjectDataSource.saveContractProjects(requestDto.uuid(), requestDto.uuid1());
    }

    @Transactional
    public void deleteContractProject(@Valid IntermediateRequestDto requestDto) {
        contractProjectDataSource.deleteContractProjects(requestDto.uuid(), requestDto.uuid1());
    }

    @Transactional(readOnly = true)
    public List<ContractResponseDto> getContractsByProjectId(UUID id) {
        return contractProjectDataSource.getContractsByProjectId(id);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDto> getProjectsByContractId(UUID id) {
        return contractProjectDataSource.getProjectsByContractId(id);
    }
}
