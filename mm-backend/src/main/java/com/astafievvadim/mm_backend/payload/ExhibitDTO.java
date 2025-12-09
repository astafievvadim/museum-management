package com.astafievvadim.mm_backend.payload;

import java.util.Date;
import java.util.List;

public class ExhibitDTO {

    private Long id;
    private String name;
    private String description;
    private Date creationDate;
    private Date endDate;
    private Long hallId;
    private List<Long> itemIds;

    public ExhibitDTO() {}

    public ExhibitDTO(Long id, String name, String description, Date creationDate, Date endDate, Long hallId, List<Long> itemIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.endDate = endDate;
        this.hallId = hallId;
        this.itemIds = itemIds;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    public List<Long> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Long> itemIds) {
        this.itemIds = itemIds;
    }
}
