package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.datasource.impl.AvatarDataSourceImpl;
import com.pm.EnterpriseResourcePlanning.entity.AvatarEntity;
import com.pm.EnterpriseResourcePlanning.enums.ErrorMessages;
import com.pm.EnterpriseResourcePlanning.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AvatarUseCase {

    private final AvatarDataSourceImpl dataSource;

    @Value("${storageLocation}")
    private String storageUrl;

    public AvatarEntity saveFile(MultipartFile file, UUID userId) throws IOException {

        if (file.isEmpty()) throw new RuntimeException();

        String fileName = extractExt(file);

        Path root = Paths.get(storageUrl);

        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }

        Files.copy(file.getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

        return dataSource.saveAvatar(fileName, userId);
    }

    public void updateAvatar(MultipartFile file, UUID id) throws IOException {

        String avatar = dataSource.getAvatarById(id).url();
        Path path = Paths.get(storageUrl + avatar);
        Files.deleteIfExists(path);

        if (file.isEmpty()) throw new BadRequestException();

        String fileName = extractExt(file);

        Path root = Paths.get(storageUrl);

        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }

        Files.copy(file.getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

        dataSource.updateAvatar(fileName, id);

    }

    public Resource getAvatarById(UUID userId) {

        String imageName = dataSource.getAvatarById(userId).url();

        Path path = Paths.get(storageUrl + imageName);

        Resource resource = new FileSystemResource(path);

        if (!resource.exists()) {
            throw new NotFoundException(ErrorMessages.FILE_NOT_FOUND, userId);
        }

        return resource;
    }

    public void deleteAvatar(UUID userId) throws IOException {

        String avatar = dataSource.getAvatarById(userId).url();

        removePhysicallyFile(avatar);
    }

    private String extractExt(MultipartFile file) {

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        return UUID.randomUUID() + "." + extension;
    }

    private void removePhysicallyFile(String name) throws IOException {

        Path path = Paths.get(storageUrl + name);

        Files.deleteIfExists(path);
    }
}
