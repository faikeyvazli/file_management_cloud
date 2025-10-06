package com.project.filestorage.service;

import com.project.filestorage.config.MinioConfig;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BucketService {
    private static final Logger logger = LoggerFactory.getLogger(BucketService.class);

    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    public BucketService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @PostConstruct
    public void init() throws Exception {
        createBucketIfNotExists(minioClient);

    }

    public void createBucketIfNotExists(MinioClient minioClient) {
        BucketExistsArgs beArgs = BucketExistsArgs.builder()
                .bucket(bucketName)
                .build();

        MakeBucketArgs mbArgs = MakeBucketArgs.builder()
                .bucket(bucketName)
                .build();

        try {
            if (!minioClient.bucketExists(beArgs)) {
                logger.info("Bucket '{}' not found. Creating new bucket...", bucketName);
                minioClient.makeBucket(mbArgs);
            } else {
                logger.info("Bucket '{}' found. ", bucketName);
            }
        } catch (Exception e) {
            logger.error("Bucket verification or creation failed.");
        }

    }
}
