package com.astafievvadim.mm_backend.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Temporal(TemporalType.DATE)
    private Date year;

    @Enumerated(EnumType.STRING)
    private ItemTypeEnum type;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "file_metadata_id")
    private FileMetadata fileMetadata;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany(mappedBy = "items")
    private List<Exhibit> exhibits;

    // No-args constructor
    public Item() {
    }

    // All-args constructor
    public Item(String name, String description, Date year, ItemTypeEnum type,
                FileMetadata fileMetadata, Author author, List<Exhibit> exhibits) {
        this.name = name;
        this.description = description;
        this.year = year;
        this.type = type;
        this.fileMetadata = fileMetadata;
        this.author = author;
        this.exhibits = exhibits;
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

    public FileMetadata getFileMetadata() {
        return fileMetadata;
    }

    public void setFileMetadata(FileMetadata fileMetadata) {
        this.fileMetadata = fileMetadata;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Exhibit> getExhibits() {
        return exhibits;
    }

    public void setExhibits(List<Exhibit> exhibits) {
        this.exhibits = exhibits;
    }

    // equals & hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        return Objects.equals(id, item.id) &&
                Objects.equals(name, item.name) &&
                Objects.equals(description, item.description) &&
                Objects.equals(year, item.year) &&
                type == item.type &&
                Objects.equals(fileMetadata, item.fileMetadata) &&
                Objects.equals(author, item.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, year, type, fileMetadata, author);
    }
}
