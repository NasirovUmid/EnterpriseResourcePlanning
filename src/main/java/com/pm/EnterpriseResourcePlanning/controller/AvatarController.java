package com.pm.EnterpriseResourcePlanning.controller;

import com.pm.EnterpriseResourcePlanning.usecases.AvatarUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/avatars")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarUseCase avatarUseCase;

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAvatar(@PathVariable(name = "id")UUID id, String url) {

        avatarUseCase.updateAvatar(url,id);

        return ResponseEntity.ok().build();
    }
}
