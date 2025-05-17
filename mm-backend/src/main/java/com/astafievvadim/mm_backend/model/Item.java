package com.astafievvadim.mm_backend.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Date year;

    @Enumerated
    private ItemTypeEnum type;

    @OneToOne
    @JoinColumn(name="fileDataId")
    private FileData fileData;

    public Item() {
    }

    public Item(String name, String description, Date year, ItemTypeEnum type, FileData fileData) {
        this.name = name;
        this.description = description;
        this.year = year;
        this.type = type;
        this.fileData = fileData;
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

    public FileData getFileData() {
        return fileData;
    }

    public void setFileData(FileData fileData) {
        this.fileData = fileData;
    }
}
