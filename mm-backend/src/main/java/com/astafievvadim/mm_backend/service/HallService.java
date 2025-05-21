package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Gallery;
import com.astafievvadim.mm_backend.model.Hall;
import com.astafievvadim.mm_backend.repo.GalleryRepo;
import com.astafievvadim.mm_backend.repo.HallRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallService {

    private final HallRepo hallRepo;
    private final GalleryRepo galleryRepo;

    @Autowired
    public HallService(HallRepo hallRepo, GalleryRepo galleryRepo) {
        this.hallRepo = hallRepo;
        this.galleryRepo = galleryRepo;
    }

    public List<Hall> findAll() {
        return hallRepo.findAll();
    }

    public Hall findById(Long id) {
        return hallRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hall not found with id: " + id));
    }

    public Hall create(Hall hall) {
        Gallery gallery = galleryRepo.findById(hall.getGallery().getId())
                .orElseThrow(() -> new RuntimeException("Gallery not found with id: " + hall.getGallery().getId()));

        Hall newHall = new Hall();
        newHall.setName(hall.getName());
        newHall.setGallery(gallery);

        return hallRepo.save(newHall);
    }

    public Hall update(Hall hall, Long id) {
        Hall existingHall = hallRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hall not found with id: " + id));

        Gallery gallery = galleryRepo.findById(hall.getGallery().getId())
                .orElseThrow(() -> new RuntimeException("Gallery not found with id: " + hall.getGallery().getId()));

        existingHall.setName(hall.getName());
        existingHall.setGallery(gallery);

        return hallRepo.save(existingHall);
    }

    public void delete(Long id) {
        Hall hall = hallRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hall not found with id: " + id));

        hallRepo.delete(hall);
    }
}
