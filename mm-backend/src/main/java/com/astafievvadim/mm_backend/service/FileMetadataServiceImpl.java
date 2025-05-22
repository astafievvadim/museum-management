package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.FileMetadata;
import com.astafievvadim.mm_backend.model.Item;
import com.astafievvadim.mm_backend.payload.FileResponse;
import com.astafievvadim.mm_backend.repo.FileMetadataRepo;
import com.astafievvadim.mm_backend.repo.ItemRepo;
import com.jlefebure.spring.boot.minio.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service
public class FileMetadataServiceImpl implements FileMetadataService {

    private final MinioStorageService minioStorageService;
    private final FileMetadataRepo fileMetadataRepository;
    private final ItemRepo itemRepository;

    @Autowired
    public FileMetadataServiceImpl(MinioStorageService minioStorageService,
                                   FileMetadataRepo fileMetadataRepository,
                                   ItemRepo itemRepository) {
        this.minioStorageService = minioStorageService;
        this.fileMetadataRepository = fileMetadataRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public FileResponse addFile(MultipartFile file, Long itemId) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            minioStorageService.upload(inputStream, filename, file.getContentType());

            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new RuntimeException("Item not found"));

            FileMetadata metadata = new FileMetadata();
            metadata.setFilename(filename);
            metadata.setContentType(file.getContentType());
            metadata.setSize(file.getSize());
            metadata.setLastModified(new Date());
            fileMetadataRepository.save(metadata);

            return new FileResponse(
                    filename,
                    file.getContentType(),
                    file.getSize(),
                    new Date(),
                    null,
                    metadata.getId()
            );
        } catch (Exception e) {
            throw new RuntimeException("File upload failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteFile(String filename) throws MinioException {
        try {
            minioStorageService.delete(filename);

            FileMetadata metadata = fileMetadataRepository.findByFilename(filename);
            if (metadata != null) {
                fileMetadataRepository.delete(metadata);
            }
        } catch (Exception e) {
            throw new MinioException("Failed to delete file: " + e.getMessage(), e);
        }
    }

    @Override
    public FileResponse getFile(String filename) throws MinioException {
        try {
            FileMetadata metadata = fileMetadataRepository.findByFilename(filename);
            if (metadata == null) {
                throw new Exception("File metadata not found for: " + filename);
            }

            InputStream inputStream = minioStorageService.getObject(filename);

            return new FileResponse(
                    metadata.getFilename(),
                    metadata.getContentType(),
                    metadata.getSize(),
                    metadata.getLastModified(),
                    new InputStreamResource(inputStream),
                    metadata.getId()
            );
        } catch (Exception e) {
            throw new MinioException("Could not fetch file or metadata: " + e.getMessage(), e);
        }
    }

    @Override
    public FileResponse getFileDetails(String filename) {
        FileMetadata metadata = fileMetadataRepository.findByFilename(filename);
        if (metadata == null) {
            throw new RuntimeException("File metadata not found for: " + filename);
        }

        return new FileResponse(
                metadata.getFilename(),
                metadata.getContentType(),
                metadata.getSize(),
                metadata.getLastModified(),
                null,
                metadata.getId()
        );
    }

    @Override
    public FileMetadata findById(Long id) {
        return fileMetadataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FileMetadata not found with id: " + id));
    }

    @Override
    public FileResponse replaceFileForItem(MultipartFile file, Long itemId) throws Exception {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        FileMetadata oldMetadata = fileMetadataRepository.findByItem(item);

        if (oldMetadata != null) {
            // Delete file from MinIO
            minioStorageService.delete(oldMetadata.getFilename());

            // Unlink old metadata from item and save item first
            item.setFileMetadata(null);
            itemRepository.save(item);

            // Delete old metadata and flush to DB immediately
            fileMetadataRepository.delete(oldMetadata);
            fileMetadataRepository.flush();
        }

        try (InputStream inputStream = file.getInputStream()) {
            String newFilename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            minioStorageService.upload(inputStream, newFilename, file.getContentType());

            FileMetadata newMetadata = new FileMetadata();
            newMetadata.setFilename(newFilename);
            newMetadata.setContentType(file.getContentType());
            newMetadata.setSize(file.getSize());
            newMetadata.setLastModified(new Date());
            // Save new metadata
            fileMetadataRepository.save(newMetadata);
            fileMetadataRepository.flush();

            // Link new metadata and save item
            item.setFileMetadata(newMetadata);
            itemRepository.save(item);

            return new FileResponse(
                    newFilename,
                    file.getContentType(),
                    file.getSize(),
                    new Date(),
                    null,
                    newMetadata.getId()
            );
        }
    }
}
