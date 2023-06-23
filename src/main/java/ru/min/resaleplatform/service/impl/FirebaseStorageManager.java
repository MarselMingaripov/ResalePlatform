package ru.min.resaleplatform.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class FirebaseStorageManager {

    private final Storage storage;

    @Value("${key.storage.path}")
    private String key;

    public FirebaseStorageManager() {
        try {
            log.info(key);
            FileInputStream serviceAccount = new FileInputStream(key);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);

            storage = StorageOptions.getDefaultInstance().getService();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize Firebase SDK");
        }
    }

    public void uploadImage(String imagePath, String imageName) throws IOException {
        String bucketName = "gs://resale-platform-skypro.appspot.com";
        BlobId blobId = BlobId.of(bucketName, imageName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, Files.readAllBytes(Paths.get(imagePath)));
        System.out.println("Image uploaded successfully!");
    }

    public String getImageUrl(String imageName) {
        String bucketName = "gs://resale-platform-skypro.appspot.com";

        String imageUrl = "https://storage.googleapis.com/" + bucketName + "/" + imageName;

        return imageUrl;
    }

    public byte[] getImageBytes(String imageName) {
        // Получение ссылки на бакет Firebase Storage
        String bucketName = "gs://resale-platform-skypro.appspot.com";
        Bucket bucket = storage.get(bucketName);

        // Получение объекта Blob (изображения) по имени
        Blob blob = bucket.get(imageName);

        // Получение содержимого изображения в виде массива байтов
        return blob.getContent();
    }
}
