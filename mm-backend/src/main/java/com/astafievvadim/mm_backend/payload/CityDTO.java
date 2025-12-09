package com.astafievvadim.mm_backend.payload;

public class CityDTO {

    private Long id;
    private String name;
    private String postalCode;
    private Long countryId;

    public CityDTO() {}

    public CityDTO(Long id, String name, String postalCode, Long countryId) {
        this.id = id;
        this.name = name;
        this.postalCode = postalCode;
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
