package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.configuration.MinioConfig;
import io.minio.*;
import io.minio.messages.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class MinioStorageService {
    private final MinioClient minioClient;

    public MinioStorageService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public StatObjectResponse getMetadata(String filename) throws Exception {
        return minioClient.statObject(
                StatObjectArgs.builder()
                        .bucket(MinioConfig.COMMON_BUCKET_NAME)
                        .object(filename)
                        .build()
        );
    }

    public InputStream getObject(String filename) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(MinioConfig.COMMON_BUCKET_NAME)
                        .object(filename)
                        .build()
        );
    }

    public String upload(InputStream inputStream, String filename, String contentType) throws Exception {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(MinioConfig.COMMON_BUCKET_NAME)
                        .object(filename)
                        .stream(inputStream, -1, 10485760)
                        .contentType(contentType)
                        .build()
        );
        return filename;
    }

    public void delete(String filename) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(MinioConfig.COMMON_BUCKET_NAME)
                        .object(filename)
                        .build()
        );
    }

    public boolean isObjectExist(String filename) throws Exception {
        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(MinioConfig.COMMON_BUCKET_NAME)
                            .object(filename)
                            .build()
            );
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public List<Bucket> listBuckets() throws Exception {
        return minioClient.listBuckets();
    }
}
