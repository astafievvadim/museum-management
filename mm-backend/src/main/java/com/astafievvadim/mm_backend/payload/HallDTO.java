package com.astafievvadim.mm_backend.payload;

public class HallDTO {

    private Long id;
    private String name;
    private Long galleryId;

    public HallDTO() {}

    public HallDTO(Long id, String name, Long galleryId) {
        this.id = id;
        this.name = name;
        this.galleryId = galleryId;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(Long galleryId) {
        this.galleryId = galleryId;
    }
}
