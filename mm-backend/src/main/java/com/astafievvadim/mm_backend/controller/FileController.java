package com.astafievvadim.mm_backend.controller;

import com.astafievvadim.mm_backend.model.FileMetadata;
import com.astafievvadim.mm_backend.model.Item;
import com.astafievvadim.mm_backend.payload.FileResponse;
import com.astafievvadim.mm_backend.service.FileMetadataService;
import com.astafievvadim.mm_backend.service.ItemService;
import com.jlefebure.spring.boot.minio.MinioException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:3000")
public class FileController {

    private final FileMetadataService fileMetadataService;
    private final ItemService itemService;
    public FileController(FileMetadataService fileMetadataService, ItemService itemService) {
        this.fileMetadataService = fileMetadataService;
        this.itemService = itemService;
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Item> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("itemId") Long itemId) throws IOException, MinioException {

        fileMetadataService.addFile(file, itemId);

        Item updatedItem = itemService.findById(itemId);
        return ResponseEntity.ok(updatedItem);
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
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteFile(@PathVariable String filename) throws MinioException {
        fileMetadataService.deleteFile(filename);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/replace")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<FileResponse> replaceFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("itemId") Long itemId) throws Exception {

        FileResponse updatedFileResponse = fileMetadataService.replaceFileForItem(file, itemId);
        return ResponseEntity.ok(updatedFileResponse);
    }


}
