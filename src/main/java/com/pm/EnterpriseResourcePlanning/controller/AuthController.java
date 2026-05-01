package com.pm.EnterpriseResourcePlanning.controller;

import com.pm.EnterpriseResourcePlanning.dto.requestdtos.AuthPasswordRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.AuthRefreshTokenDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.AuthRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.requestdtos.UserRequestDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.AuthUserResponseDto;
import com.pm.EnterpriseResourcePlanning.dto.responsdtos.JwtAuthenticationResponseDto;
import com.pm.EnterpriseResourcePlanning.usecases.AuthUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AuthUserResponseDto> register(@Valid @RequestPart("data") UserRequestDto userRequestDto,
                                                        @RequestPart(value = "image") MultipartFile file) throws IOException, NoSuchAlgorithmException {

        log.info("{}", userRequestDto);

        AuthUserResponseDto authUserResponseDto = authUseCase.register(userRequestDto, file);

        return ResponseEntity.status(201).body(authUserResponseDto);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthUserResponseDto> login(@Valid @RequestBody AuthRequestDto authRequestDto) throws NoSuchAlgorithmException {

        System.out.println(authRequestDto);

        log.info("{}", authRequestDto);

        AuthUserResponseDto responseDto = authUseCase.login(authRequestDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponseDto> refresh(@Valid @RequestBody AuthRefreshTokenDto refreshTokenDto) throws NoSuchAlgorithmException {

        JwtAuthenticationResponseDto responseDto = authUseCase.refresh(refreshTokenDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/log-out")
    public ResponseEntity<Void> logOut(@Valid @RequestBody AuthRefreshTokenDto authRequestDto) throws NoSuchAlgorithmException {

        authUseCase.logOut(authRequestDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/password")
    public ResponseEntity<JwtAuthenticationResponseDto> changePassword(@Valid @RequestBody AuthPasswordRequestDto passwordRequestDto) throws NoSuchAlgorithmException {

        JwtAuthenticationResponseDto responseDto = authUseCase.changePassword(passwordRequestDto);

        return ResponseEntity.ok().body(responseDto);
    }
}
