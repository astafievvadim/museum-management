package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.City;
import com.astafievvadim.mm_backend.model.Country;
import com.astafievvadim.mm_backend.repo.CityRepo;
import com.astafievvadim.mm_backend.repo.CountryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepo cityRepo;
    private final CountryRepo countryRepo;

    @Autowired
    public CityService(CityRepo cityRepo, CountryRepo countryRepo) {
        this.cityRepo = cityRepo;
        this.countryRepo = countryRepo;
    }

    public List<City> findAll() {
        return cityRepo.findAll();
    }

    public City findById(Long id) {
        return cityRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found with id: " + id));
    }

    public City create(City city, Long countryId) {
        Country country = countryRepo.findById(countryId)
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + countryId));

        City newCity = new City();
        newCity.setName(city.getName());
        newCity.setPostalCode(city.getPostalCode());
        newCity.setCountry(country);

        return cityRepo.save(newCity);
    }

    public City update(City city, Long id) {
        City existingCity = cityRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found with id: " + id));

        if (city.getCountry() != null && city.getCountry().getId() != null) {
            Country country = countryRepo.findById(city.getCountry().getId())
                    .orElseThrow(() -> new RuntimeException("Country not found with id: " + city.getCountry().getId()));
            existingCity.setCountry(country);
        }

        existingCity.setName(city.getName());
        existingCity.setPostalCode(city.getPostalCode());

        return cityRepo.save(existingCity);
    }

    public void delete(Long id) {
        City city = cityRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found with id: " + id));
        cityRepo.delete(city);
    }
}
