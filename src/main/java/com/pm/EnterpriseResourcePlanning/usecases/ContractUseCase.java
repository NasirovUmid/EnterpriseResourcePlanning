package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.datasource.ContractDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.ContractProjectDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.OrganizationContractDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.SortResolver;
import com.pm.EnterpriseResourcePlanning.dto.filters.ContractFilterDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ContractUpdateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.ContractsRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.LinkRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ContractResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.ProjectResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.ContractsEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.enums.SortType;
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

    @Transactional(rollbackFor = Exception.class)
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

        Sort sort1 = SortResolver.resolver(SortType.CONTRACT, sort);

        Pageable pageable = PageRequest.of(page, size, sort1);

        return contractDataSource.getContractsPage(specification, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public void linkOrganizationContract(@Valid LinkRequestDto organizationContractRequestDto) {

        UUID organizationId = organizationContractRequestDto.entityId();
        UUID contractId = organizationContractRequestDto.relatedEntityId();

        if (organizationContractDataSource.exists(organizationId, contractId)) {
            throw new AlreadyExistsException(ErrorMessages.ORGANIZATION_CONTRACT_ALREADY_EXISTS, organizationId, contractId);
        }
        organizationContractDataSource.linkOrganizationContract(organizationId, contractId);
    }

    public boolean exists(@Valid LinkRequestDto organizationContractRequestDto) {

        UUID organizationId = organizationContractRequestDto.entityId();
        UUID contractId = organizationContractRequestDto.relatedEntityId();

        return organizationContractDataSource.exists(organizationId, contractId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteOrganizationContract(LinkRequestDto organizationContractRequestDto) {

        UUID organizationId = organizationContractRequestDto.entityId();
        UUID contractId = organizationContractRequestDto.relatedEntityId();

        organizationContractDataSource.removeOrganizationContractLink(organizationId, contractId);
    }

    @Transactional(readOnly = true)
    public List<ContractResponseDto> getOrganizationsContracts(UUID id) {
        return organizationContractDataSource.getOrganizationContracts(id);
    }

    @Transactional(readOnly = true)
    public ContractResponseDto getContractById(UUID id) {
        return contractDataSource.getContractById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateContract(UUID id, ContractUpdateRequestDto requestDto) {
        if (requestDto.startDate() != null && requestDto.endDate() != null &&
                (requestDto.startDate().isAfter(requestDto.endDate()) || requestDto.startDate().equals(requestDto.endDate()))) {
            throw new MethodArgumentNotValidException(ErrorMessages.INVALID_DATES);
        }

        contractDataSource.updateContracts(requestDto.amount(), requestDto.startDate(), requestDto.endDate(), id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteContract(UUID id) {
        contractDataSource.deleteContract(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveContractProject(@Valid LinkRequestDto requestDto) {

        UUID contractId = requestDto.entityId();
        UUID projectId = requestDto.relatedEntityId();

        contractProjectDataSource.saveContractProjects(contractId, projectId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteContractProject(@Valid LinkRequestDto requestDto) {

        UUID contractId = requestDto.entityId();
        UUID projectId = requestDto.relatedEntityId();

        contractProjectDataSource.deleteContractProjects(contractId, projectId);
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
