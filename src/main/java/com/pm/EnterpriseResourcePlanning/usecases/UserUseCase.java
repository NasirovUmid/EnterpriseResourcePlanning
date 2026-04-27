package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.dao.impl.RoleDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.impl.UserDataSourceImpl;
import com.pm.EnterpriseResourcePlanning.datasource.impl.UserRoleDataSourceImpl;
import com.pm.EnterpriseResourcePlanning.dto.filters.UserFilterDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.IntermediateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.UserRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.UserUpdateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.RoleResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.UserResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.UserEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.enums.RoleStatus;
import com.pm.EnterpriseResourcePlanning.enums.UserStatus;
import com.pm.EnterpriseResourcePlanning.exceptions.AlreadyExistsException;
import com.pm.EnterpriseResourcePlanning.exceptions.IllegalStateException;
import com.pm.EnterpriseResourcePlanning.specifications.UserSpecifications;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserUseCase {

    private final UserDataSourceImpl userDataSource;
    private final AvatarUseCase avatarUseCase;
    private final UserRoleDataSourceImpl userRoleDataSource;
    private final RoleDaoImpl roleDao;

    public UserResponseDto createUser(UserRequestDto userRequestDto, MultipartFile avatar) throws IOException {

        if (userDataSource.existsByUsername(userRequestDto.username())) {
            throw new AlreadyExistsException(ErrorMessages.USER_ALREADY_EXISTS, userRequestDto.username());
        }

        UserResponseDto responseDto = userDataSource.createUser(userRequestDto.fullname(), userRequestDto.username(), userRequestDto.password(), userRequestDto.phoneNumber());

        avatarUseCase.saveFile(avatar, responseDto.id());

        return responseDto;
    }

    public Page<UserResponseDto> getUsersPage(int page, int size, UserFilterDto userFilterDto, String sort) {

        Specification<UserEntity> specification = UserSpecifications.build(userFilterDto.fullName(), userFilterDto.username(), userFilterDto.phoneNumber(), userFilterDto.userStatus());

        return userDataSource.getUsersPage(specification, PageRequest.of(page, size, toUserEntitySort(sort)));
    }

    public void updateUser(@Valid UserUpdateRequestDto userRequestDto, UUID id) {

        if (userRequestDto == null || (userRequestDto.fullName() == null && userRequestDto.phoneNumber() == null &&
                userRequestDto.password() == null && userRequestDto.avatarId() == null)) {
            throw new RuntimeException();
        }
        userDataSource.updateUser(id, userRequestDto.fullName(), userRequestDto.password(), userRequestDto.avatarId());
    }

    public UserResponseDto getUserById(UUID id) {

        return userDataSource.getUserById(id);
    }

    public void deactivateUser(UUID id) {
        userDataSource.deactivateUser(id);
    }

    public boolean checkUser(UUID id, String moduleName, String actionName) {

        if (userDataSource.getUserById(id).status().equals(UserStatus.DEACTIVATED)) {
            throw new IllegalStateException(ErrorMessages.USER_IS_DEACTIVATED, id);
        }
        return userDataSource.checkAccess(id, moduleName, actionName);
    }

    public void connectUserToRole(@Valid IntermediateRequestDto userRoleRequestDto) {

        if (roleDao.getRoleById(userRoleRequestDto.uuid1()).getStatus().equals(RoleStatus.DEACTIVATED)) {
            throw new IllegalStateException(ErrorMessages.ROLE_IS_DEACTIVATED, userRoleRequestDto.uuid(), userRoleRequestDto.uuid1());
        }

        UserResponseDto user = userDataSource.getUserById(userRoleRequestDto.uuid());

        if (user.status().equals(UserStatus.DEACTIVATED)) {
            throw new IllegalStateException(ErrorMessages.USER_IS_DEACTIVATED, userRoleRequestDto.uuid(), userRoleRequestDto.uuid1());
        }

        if (userRoleExists(userRoleRequestDto)) {
            throw new AlreadyExistsException(ErrorMessages.USER_ROLE_ALREADY_EXISTS, userRoleRequestDto.uuid(), userRoleRequestDto.uuid1());
        }

        userRoleDataSource.saveUserRole(userRoleRequestDto.uuid(), userRoleRequestDto.uuid1());
    }

    public void deleteUserRoleLink(@Valid IntermediateRequestDto userRoleRequestDto) {
        userRoleDataSource.removeUserRoleLink(userRoleRequestDto.uuid(), userRoleRequestDto.uuid1());
    }

    public Boolean userRoleExists(@Valid IntermediateRequestDto userRoleRequestDto) {
        return userRoleDataSource.exists(userRoleRequestDto.uuid(), userRoleRequestDto.uuid1());
    }

    public List<RoleResponseDto> getUserRoles(UUID id) {
        return userRoleDataSource.findRolesByUserId(id);
    }

    public static Sort toUserEntitySort(String sort) {
        if (sort == null) return Sort.by("fullName").ascending();

        String s = sort.trim().toLowerCase();
        if (s.equals("fullName,desc")) return Sort.by("fullName").descending();
        if (s.equals("fullName,asc")) return Sort.by("fullName").ascending();

        if (s.equals("username,desc")) return Sort.by("username").descending();
        if (s.equals("username,asc")) return Sort.by("username").ascending();

        if (s.equals("userStatus,desc")) return Sort.by("userStatus").descending();
        if (s.equals("userStatus,asc")) return Sort.by("userStatus").ascending();

        return Sort.by("fullName").ascending();
    }
}
