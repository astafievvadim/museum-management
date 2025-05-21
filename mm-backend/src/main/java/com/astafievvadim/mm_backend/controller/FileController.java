package com.astafievvadim.mm_backend.controller;

import com.astafievvadim.mm_backend.model.FileMetadata;
import com.astafievvadim.mm_backend.payload.FileResponse;
import com.astafievvadim.mm_backend.service.FileMetadataService;
import com.jlefebure.spring.boot.minio.MinioException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileMetadataService fileMetadataService;

    public FileController(FileMetadataService fileMetadataService) {
        this.fileMetadataService = fileMetadataService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("itemId") Long itemId) throws IOException, MinioException {
        return ResponseEntity.ok(fileMetadataService.addFile(file, itemId));
    }

    @GetMapping("/{filename}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String filename) throws IOException, MinioException {
        FileResponse response = fileMetadataService.getFile(filename);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + response.getFilename() + "\"")
                .contentType(MediaType.parseMediaType(response.getContentType()))
                .body(response.getStream());
    }

    @GetMapping("/{filename}/details")
    public ResponseEntity<FileResponse> getFileDetails(@PathVariable String filename) {
        return ResponseEntity.ok(fileMetadataService.getFileDetails(filename));
    }

    @DeleteMapping("/{filename}")
    public ResponseEntity<Void> deleteFile(@PathVariable String filename) throws MinioException {
        fileMetadataService.deleteFile(filename);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/replace")
    public ResponseEntity<FileResponse> replaceFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("itemId") Long itemId) throws Exception {

        FileResponse updatedFileResponse = fileMetadataService.replaceFileForItem(file, itemId);
        return ResponseEntity.ok(updatedFileResponse);
    }


}
