package com.astafievvadim.mm_backend.model;

import ch.qos.logback.core.model.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private String contentType;

    private Long size;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    // Link to your main entity, e.g., a Model entity
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @JsonIgnore
    private Item item;


    public FileMetadata() {
    }

    public FileMetadata(String filename, String contentType, Long size, Date lastModified, Item item) {
        this.filename = filename;
        this.contentType = contentType;
        this.size = size;
        this.lastModified = lastModified;
        this.item = item;
    }

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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileMetadata that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(filename, that.filename) && Objects.equals(contentType, that.contentType) && Objects.equals(size, that.size) && Objects.equals(lastModified, that.lastModified) && Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filename, contentType, size, lastModified, item);
    }
}
