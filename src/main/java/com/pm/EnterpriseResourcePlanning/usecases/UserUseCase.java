package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.dao.RoleDao;
import com.pm.EnterpriseResourcePlanning.datasource.UserDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.UserRoleDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.helper.SortResolver;
import com.pm.EnterpriseResourcePlanning.dto.filters.UserFilterDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.LinkRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.UserRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.UserUpdateRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.RoleResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.UserResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.UserEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.enums.RoleStatus;
import com.pm.EnterpriseResourcePlanning.enums.SortType;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserUseCase {

    private final UserDataSource userDataSource;
    private final AvatarUseCase avatarUseCase;
    private final UserRoleDataSource userRoleDataSource;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public UserResponseDto createUser(UserRequestDto userRequestDto, MultipartFile avatar) throws IOException {

        if (userDataSource.existsByUsername(userRequestDto.username())) {
            throw new AlreadyExistsException(ErrorMessages.USER_ALREADY_EXISTS, userRequestDto.username());
        }

        UserResponseDto responseDto = userDataSource.createUser(userRequestDto.fullname(), userRequestDto.username(), passwordEncoder.encode(userRequestDto.password()), userRequestDto.phoneNumber());

        avatarUseCase.saveFile(avatar, responseDto.id());

        return responseDto;
    }

    @Transactional(readOnly = true)
    public Page<UserResponseDto> getUsersPage(int page, int size, UserFilterDto userFilterDto, String sort) {

        Specification<UserEntity> specification = UserSpecifications.build(userFilterDto.fullName(),
                userFilterDto.username(), userFilterDto.phoneNumber(), userFilterDto.userStatus());

        Sort sort1 = SortResolver.resolver(SortType.USER, sort);

        return userDataSource.getUsersPage(specification, PageRequest.of(page, size, sort1));
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUser(@Valid UserUpdateRequestDto userRequestDto, UUID id) {

        if (userRequestDto == null || (userRequestDto.fullName() == null && userRequestDto.phoneNumber() == null)) {
            throw new RuntimeException();
        }
        userDataSource.updateUser(id, userRequestDto.fullName(), userRequestDto.phoneNumber());
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(UUID id) {

        return userDataSource.getUserById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deactivateUser(UUID id) {
        userDataSource.deactivateUser(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void connectUserToRole(@Valid LinkRequestDto userRoleRequestDto) {

        UUID userId = userRoleRequestDto.entityId();
        UUID roleId = userRoleRequestDto.relatedEntityId();

        if (roleDao.getRoleById(roleId).getStatus().equals(RoleStatus.DEACTIVATED)) {
            throw new IllegalStateException(ErrorMessages.ROLE_IS_DEACTIVATED, userId, roleId);
        }

        UserResponseDto user = userDataSource.getUserById(userId);

        if (user.status().equals(UserStatus.DEACTIVATED)) {
            throw new IllegalStateException(ErrorMessages.USER_IS_DEACTIVATED, userId, roleId);
        }

        if (userRoleExists(userRoleRequestDto)) {
            throw new AlreadyExistsException(ErrorMessages.USER_ROLE_ALREADY_EXISTS, userId, roleId);
        }

        userRoleDataSource.saveUserRole(userId, roleId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRoleLink(@Valid LinkRequestDto userRoleRequestDto) {

        UUID userId = userRoleRequestDto.entityId();
        UUID roleId = userRoleRequestDto.relatedEntityId();

        userRoleDataSource.removeUserRoleLink(userId, roleId);
    }

    public Boolean userRoleExists(@Valid LinkRequestDto userRoleRequestDto) {

        UUID userId = userRoleRequestDto.entityId();
        UUID roleId = userRoleRequestDto.relatedEntityId();

        return userRoleDataSource.exists(userId, roleId);
    }

    @Transactional(readOnly = true)
    public List<RoleResponseDto> getUserRoles(UUID id) {
        return userRoleDataSource.findRolesByUserId(id);
    }

}
