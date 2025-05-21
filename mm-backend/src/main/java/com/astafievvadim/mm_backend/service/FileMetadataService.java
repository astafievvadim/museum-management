package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.FileMetadata;
import com.astafievvadim.mm_backend.model.Item;
import com.astafievvadim.mm_backend.payload.FileResponse;
import com.jlefebure.spring.boot.minio.MinioException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileMetadataService {

    FileResponse addFile(MultipartFile file, Long itemId) throws IOException, MinioException;

    void deleteFile(String fileName) throws MinioException;

    FileResponse getFile(String fileName) throws MinioException;

    FileResponse getFileDetails(String fileName);

    FileMetadata findById(Long id);

    FileResponse replaceFileForItem(MultipartFile file, Long itemId) throws Exception;
}
