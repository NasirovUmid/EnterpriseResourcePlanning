package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.configuration.CustomUserDetails;
import com.pm.EnterpriseResourcePlanning.dao.impl.UserDaoImpl;
import com.pm.EnterpriseResourcePlanning.datasource.RefreshTokenDataSource;
import com.pm.EnterpriseResourcePlanning.datasource.UserDataSource;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.AuthPasswordRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.AuthRefreshTokenDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.AuthRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.UserRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.AuthUserResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.JwtAuthenticationResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.UserResponseDto;
import com.pm.EnterpriseResourcePlanning.entity.RefreshTokenEntity;
import com.pm.EnterpriseResourcePlanning.entity.UserEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.enums.TokenType;
import com.pm.EnterpriseResourcePlanning.exceptions.AlreadyExistsException;
import com.pm.EnterpriseResourcePlanning.exceptions.BadCredentialsException;
import com.pm.EnterpriseResourcePlanning.exceptions.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthUseCase {

    private final JwtUseCase jwtUseCase;
    private final UserDataSource userDataSource;
    private final UserDaoImpl userDao;
    private final AvatarUseCase avatarUseCase;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenDataSource refreshTokenDataSource;

    @Transactional
    public AuthUserResponseDto register(@Valid UserRequestDto userRequestDto, MultipartFile avatar) throws IOException, NoSuchAlgorithmException {

        if (userDataSource.existsByUsername(userRequestDto.username())) {
            throw new AlreadyExistsException(ErrorMessages.USER_ALREADY_EXISTS, userRequestDto.username());
        }

        UserResponseDto user = userDataSource.createUser(userRequestDto.fullname(), userRequestDto.username(), passwordEncoder.encode(userRequestDto.password()), userRequestDto.phoneNumber());

        avatarUseCase.saveFile(avatar, user.id());

        JwtAuthenticationResponseDto tokens = jwtUseCase.generateAuthToken(user.username());

        refreshTokenDataSource.saveRefreshToken(user.id(), hashing(tokens.refreshToken()), Instant.now().plus(Duration.ofHours(5)));

        return new AuthUserResponseDto(user.id(), tokens.accessToken(), tokens.refreshToken());
    }

    @Transactional
    public AuthUserResponseDto login(AuthRequestDto authRequestDto) throws NoSuchAlgorithmException {

        UserEntity user = userDao.findUserByUsername(authRequestDto.email());

        if (!passwordEncoder.matches(authRequestDto.password(), user.getPassword())) {
            throw new BadCredentialsException(ErrorMessages.WRONG_CREDENTIALS, authRequestDto.email());
        }

        JwtAuthenticationResponseDto responseDto = jwtUseCase.generateAuthToken(authRequestDto.email());

        refreshTokenDataSource.saveRefreshToken(user.getId(), hashing(responseDto.refreshToken()), Instant.now().plus(Duration.ofHours(5)));

        return new AuthUserResponseDto(user.getId(), responseDto.accessToken(), responseDto.refreshToken());
    }

    @Transactional
    public JwtAuthenticationResponseDto refresh(AuthRefreshTokenDto refreshTokenDto) throws NoSuchAlgorithmException {

        RefreshTokenEntity refreshToken = refreshTokenDataSource.getRefreshByToken(hashing(refreshTokenDto.refreshToken()));

        if (refreshToken == null) {
            throw new NotFoundException(ErrorMessages.TOKEN_NOT_FOUND, refreshTokenDto.refreshToken());
        }

        String email = jwtUseCase.getEmailFromToken(refreshTokenDto.refreshToken());

        if (!jwtUseCase.extractClaims(refreshTokenDto.refreshToken()).get("type").equals(TokenType.REFRESH.name())) {
            throw new BadCredentialsException(ErrorMessages.WRONG_CREDENTIALS, email);
        }

        JwtAuthenticationResponseDto responseDto = jwtUseCase.generateAuthToken(email);

        refreshTokenDataSource.deleteByToken(hashing(refreshTokenDto.refreshToken()));

        refreshTokenDataSource.saveRefreshToken(refreshToken.getUserId(), hashing(responseDto.refreshToken()), Instant.now().plus(Duration.ofHours(5)));

        return responseDto;
    }

    @Transactional
    public void logOut(AuthRefreshTokenDto refreshTokenDto) throws NoSuchAlgorithmException {
        refreshTokenDataSource.deleteByToken(hashing(refreshTokenDto.refreshToken()));
    }

    @Transactional
    public JwtAuthenticationResponseDto changePassword(AuthPasswordRequestDto passwordRequestDto) throws NoSuchAlgorithmException {

        if (passwordRequestDto.newPassword().equals(passwordRequestDto.oldPassword())) {
            throw new BadCredentialsException(ErrorMessages.WRONG_CREDENTIALS, null);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails user)) {
            throw new NotFoundException(ErrorMessages.USER_NOT_FOUND, passwordRequestDto.oldPassword());
        }

        UserEntity user1 = userDao.getUserById(user.getId());

        if (!passwordEncoder.matches(passwordRequestDto.oldPassword(), user1.getPassword())) {
            throw new BadCredentialsException(ErrorMessages.WRONG_CREDENTIALS, user1.getUsername());
        }

        userDataSource.updateUserPassword(user1.getId(), passwordEncoder.encode(passwordRequestDto.newPassword()));
        refreshTokenDataSource.deleteAllByUserId(user1.getId());

        JwtAuthenticationResponseDto responseDto = jwtUseCase.generateAuthToken(user1.getUsername());
        refreshTokenDataSource.saveRefreshToken(user1.getId(), hashing(responseDto.refreshToken()), Instant.now().plus(Duration.ofHours(5)));

        return responseDto;
    }

    private String hashing(String token) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = messageDigest.digest(token.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }
}
