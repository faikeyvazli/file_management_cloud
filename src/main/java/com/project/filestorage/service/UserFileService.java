package com.project.filestorage.service;

import com.project.filestorage.repository.UserRepository;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class UserFileService {
    private static final Logger logger = LoggerFactory.getLogger(UserFileService.class);

    private final MinioClient minioClient;

    private final UserRepository userRepository;

    @Value("${minio.bucket-name}")
    private String bucketName;

    public UserFileService(MinioClient minioClient, UserRepository userRepository) {
        this.minioClient = minioClient;
        this.userRepository = userRepository;
    }

    // TODO: creating a folder
    public void createFolder(Long userId, String folderName) {
        String folderPath = "user-" + userId + "-files/" + folderName + "/";

        try {
            PutObjectArgs poArgs = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(folderPath)
                    .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                    .build();

            minioClient.putObject(poArgs);
            logger.info("Folder {} created successfully", folderName);
        } catch (Exception e) {
            logger.error("Could not create folder {} for user {} with id {}", folderName, userRepository.findById(userId), userId);
        }
    }


    // TODO: uploading a file
    public void uploadFile(String longId, String fileName, String objectName, String contentType) {

        try {
            UploadObjectArgs uoArgs = UploadObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .filename(fileName)
                    .contentType(contentType)
                    .build();

            ObjectWriteResponse resp = minioClient.uploadObject(uoArgs);
            logger.info("Object {} has uploaded successfully for user: {}", resp.object(), longId);
        }
        catch (Exception e){
            logger.error("Object upload has failed");
            // additional error handling here?
        }
    }
}
