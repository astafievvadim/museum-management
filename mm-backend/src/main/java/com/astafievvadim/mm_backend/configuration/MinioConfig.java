package com.astafievvadim.mm_backend.configuration;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    public static final String COMMON_BUCKET_NAME = "object-models";

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.username}")
    private String minioUsername;


    @Value("${minio.password}")
    private String minioPassword;

    @Bean
    public MinioClient minioClient() throws Exception{

        MinioClient mc = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(minioUsername,minioPassword)
                .build();

        if(!mc.bucketExists(BucketExistsArgs
                        .builder()
                        .bucket(COMMON_BUCKET_NAME)
                        .build())){
            mc.makeBucket(
                    MakeBucketArgs
                            .builder()
                            .bucket(COMMON_BUCKET_NAME)
                            .build()
            );
        }

        return mc;
    }
}
