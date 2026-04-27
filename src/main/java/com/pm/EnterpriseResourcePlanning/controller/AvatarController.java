package com.pm.EnterpriseResourcePlanning.controller;

import com.pm.EnterpriseResourcePlanning.usecases.AvatarUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/avatars")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarUseCase avatarUseCase;

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateAvatar(@PathVariable(name = "id") UUID id,
                                             @RequestPart("image") MultipartFile image) throws IOException {

        avatarUseCase.updateAvatar(image, id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getAvatarById(@PathVariable(name = "id") UUID id) {

        Resource resource = avatarUseCase.getAvatarById(id);

        return ResponseEntity.ok().body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvatar(@PathVariable(name = "id") UUID id) throws IOException {

        avatarUseCase.deleteAvatar(id);

        return ResponseEntity.ok().build();
    }

}
