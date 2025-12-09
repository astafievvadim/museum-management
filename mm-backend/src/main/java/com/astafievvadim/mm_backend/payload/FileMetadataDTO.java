package com.astafievvadim.mm_backend.payload;

import java.util.Date;

public class FileMetadataDTO {

    private Long id;
    private String filename;
    private String contentType;
    private Long size;
    private Date lastModified;
    private Long itemId;

    public FileMetadataDTO() {}

    public FileMetadataDTO(Long id, String filename, String contentType, Long size, Date lastModified, Long itemId) {
        this.id = id;
        this.filename = filename;
        this.contentType = contentType;
        this.size = size;
        this.lastModified = lastModified;
        this.itemId = itemId;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
