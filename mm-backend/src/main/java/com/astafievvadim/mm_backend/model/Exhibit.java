package com.astafievvadim.mm_backend.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Exhibit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private List<Item> items;

    private String name;

    private String description;

    private Date creationDate;

    public Exhibit() {
    }

    public Exhibit(List<Item> items, String name, String description, Date creationDate) {
        this.items = items;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
