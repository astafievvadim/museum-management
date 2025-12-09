package com.astafievvadim.mm_backend.payload;

import java.util.Date;

public class ItemDTO {

    private Long id;
    private String name;
    private String description;
    private Date year;
    private String type; // Store enum as String

    private Long fileMetadataId;
    private Long authorId;

    // We exclude exhibits list here to avoid deep circular references
    // but you can add List<Long> exhibitIds if needed

    public ItemDTO() {}

    public ItemDTO(Long id, String name, String description, Date year, String type,
                   Long fileMetadataId, Long authorId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.year = year;
        this.type = type;
        this.fileMetadataId = fileMetadataId;
        this.authorId = authorId;
    }

    // Getters & setters

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getFileMetadataId() {
        return fileMetadataId;
    }

    public void setFileMetadataId(Long fileMetadataId) {
        this.fileMetadataId = fileMetadataId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
