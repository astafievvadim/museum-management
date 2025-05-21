package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Address;
import com.astafievvadim.mm_backend.model.Gallery;
import com.astafievvadim.mm_backend.repo.AddressRepo;
import com.astafievvadim.mm_backend.repo.GalleryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GalleryService {

    private final GalleryRepo galleryRepo;
    private final AddressRepo addressRepo;

    @Autowired
    public GalleryService(GalleryRepo galleryRepo, AddressRepo addressRepo) {
        this.galleryRepo = galleryRepo;
        this.addressRepo = addressRepo;
    }

    public List<Gallery> findAll() {
        return galleryRepo.findAll();
    }

    public Gallery findById(Long id) {
        return galleryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Gallery not found with id: " + id));
    }

    public Gallery create(Gallery gallery) {
        Address address = addressRepo.findById(gallery.getAddress().getId())
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + gallery.getAddress().getId()));

        Gallery newGallery = new Gallery();
        newGallery.setName(gallery.getName());
        newGallery.setAddress(address);

        return galleryRepo.save(newGallery);
    }

    public Gallery update(Gallery gallery, Long id) {
        Gallery existingGallery = galleryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Gallery not found with id: " + id));

        Address address = addressRepo.findById(gallery.getAddress().getId())
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + gallery.getAddress().getId()));

        existingGallery.setName(gallery.getName());
        existingGallery.setAddress(address);

        return galleryRepo.save(existingGallery);
    }

    public void delete(Long id) {
        Gallery gallery = galleryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Gallery not found with id: " + id));

        galleryRepo.delete(gallery);
    }
}
