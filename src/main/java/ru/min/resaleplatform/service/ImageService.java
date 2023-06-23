package ru.min.resaleplatform.service;

import org.slf4j.Logger;

public interface ImageService {

    /**
     * создание директории
     * @param imageUploadPath
     * @param logger
     */
    void createDir(String imageUploadPath, Logger logger);
}
