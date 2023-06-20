package ru.min.resaleplatform.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.min.resaleplatform.service.ImageService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.readAllBytes;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Value("${image.upload.path}")
    private String desktopPath;

    @Override
    public void createDir(String imageUploadPath, Logger logger) {
        Path path = Paths.get(imageUploadPath);

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                logger.info("Путь успешно создан: " + path);
            } catch (IOException e) {
                logger.error("Не удалось создать путь: " + e.getMessage());
            }
        } else {
            logger.info("Путь уже существует: " + path);
        }
    }
}
