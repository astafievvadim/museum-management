package com.astafievvadim.mm_backend.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Date year;

    @Enumerated(EnumType.STRING)
    private ItemTypeEnum type;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="fileDataId")
    private FileMetadata fileMetadata;

    public Item() {
    }

    public Item(String name, String description, Date year, ItemTypeEnum type, FileMetadata fileMetaData) {
        this.name = name;
        this.description = description;
        this.year = year;
        this.type = type;
        this.fileMetadata = fileMetaData;
    }

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

    public ItemTypeEnum getType() {
        return type;
    }

    public void setType(ItemTypeEnum type) {
        this.type = type;
    }

    public FileMetadata getFileData() {
        return fileMetadata;
    }

    public void setFileData(FileMetadata fileMetadata) {
        this.fileMetadata = fileMetadata;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        return Objects.equals(id, item.id) && Objects.equals(name, item.name) && Objects.equals(description, item.description) && Objects.equals(year, item.year) && type == item.type && Objects.equals(fileMetadata, item.fileMetadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, year, type, fileMetadata);
    }
}
