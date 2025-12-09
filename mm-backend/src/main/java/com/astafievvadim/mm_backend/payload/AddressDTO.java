package com.astafievvadim.mm_backend.payload;

public class AddressDTO {

    private Long id;
    private String street;
    private Long cityId;
    private Long countryId;
    private String postalCode;

    public AddressDTO() {}

    public AddressDTO(Long id, String street, Long cityId, Long countryId, String postalCode) {
        this.id = id;
        this.street = street;
        this.cityId = cityId;
        this.countryId = countryId;
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
