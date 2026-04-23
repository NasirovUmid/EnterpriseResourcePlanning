package com.pm.EnterpriseResourcePlanning.usecases;

import com.pm.EnterpriseResourcePlanning.datasource.impl.AvatarDataSourceImpl;
import com.pm.EnterpriseResourcePlanning.entity.AvatarEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    public AvatarEntity saveFile(MultipartFile file) throws IOException {

        if (file.isEmpty()) throw new RuntimeException();

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        String fileName = storageUrl + "_" + UUID.randomUUID() + "."+extension;

        Path root = Paths.get(storageUrl);

        if (!Files.exists(root)) {

            Files.createDirectories(root);
        }

        Files.copy(file.getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

        AvatarEntity savedAvatar = dataSource.saveAvatar(fileName);

        return savedAvatar;
    }

    public void updateAvatar(String url,UUID id) {

        dataSource.updateAvatar(url,id);

    }
}
