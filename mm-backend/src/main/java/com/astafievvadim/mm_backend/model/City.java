package com.astafievvadim.mm_backend.model;


import jakarta.persistence.*;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String postal_code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cityId")
    private City city;

    public City() {
    }

    public City(String name, String postal_code, City city) {
        this.name = name;
        this.postal_code = postal_code;
        this.city = city;
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

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
