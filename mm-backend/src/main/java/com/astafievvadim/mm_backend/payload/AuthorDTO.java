package com.astafievvadim.mm_backend.payload;

import java.util.Date;
import java.util.List;

public class AuthorDTO {
    private String name;
    private String lastname;
    private Date birthDate;
    private Date deathDate;
    private String bio;
    private Long countryId;

    private List<Long> itemIds;

    public AuthorDTO() {}

    public AuthorDTO(String name, String lastname, Date birthDate, Date deathDate, String bio, Long countryId, List<Long> itemIds) {
        this.name = name;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.bio = bio;
        this.countryId = countryId;
        this.itemIds = itemIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(Date deathDate) {
        this.deathDate = deathDate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public List<Long> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Long> itemIds) {
        this.itemIds = itemIds;
    }
}
