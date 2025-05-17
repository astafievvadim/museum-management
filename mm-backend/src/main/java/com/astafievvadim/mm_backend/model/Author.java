package com.astafievvadim.mm_backend.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private List<Item> items;

    private String name;

    private String lastname;

    private Date birthDate;

    private Date deathDate;

    @ManyToOne
    @JoinColumn(name="countryId")
    private Country country;

    public Author() {
    }

    public Author(List<Item> items, String name, String lastname, Date birthDate, Date deathDate, Country country) {
        this.items = items;
        this.name = name;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.country = country;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
