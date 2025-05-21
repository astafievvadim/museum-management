package com.astafievvadim.mm_backend.controller;

import com.astafievvadim.mm_backend.model.Gallery;
import com.astafievvadim.mm_backend.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/galleries")
public class GalleryController {

    private final GalleryService galleryService;

    @Autowired
    public GalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    @GetMapping
    public List<Gallery> getAllGalleries() {
        return galleryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gallery> getGalleryById(@PathVariable Long id) {
        Gallery gallery = galleryService.findById(id);
        return ResponseEntity.ok(gallery);
    }

    @PostMapping
    public ResponseEntity<Gallery> createGallery(@RequestBody Gallery gallery) {
        Gallery createdGallery = galleryService.create(gallery);
        return ResponseEntity.ok(createdGallery);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gallery> updateGallery(@RequestBody Gallery gallery, @PathVariable Long id) {
        Gallery updatedGallery = galleryService.update(gallery, id);
        return ResponseEntity.ok(updatedGallery);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGallery(@PathVariable Long id) {
        galleryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
