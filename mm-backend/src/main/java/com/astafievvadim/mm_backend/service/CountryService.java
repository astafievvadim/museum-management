package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Country;
import com.astafievvadim.mm_backend.repo.CountryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepo countryRepo;

    @Autowired
    public CountryService(CountryRepo countryRepo) {
        this.countryRepo = countryRepo;
    }

    public List<Country> findAll() {
        return countryRepo.findAll();
    }

    public Country findById(Long id) {
        return countryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + id));
    }

    public Country create(Country country) {
        Country newCountry = new Country();
        newCountry.setName(country.getName());

        return countryRepo.save(newCountry);
    }

    public Country update(Country country, Long id) {
        Country existingCountry = countryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + id));

        existingCountry.setName(country.getName());

        return countryRepo.save(existingCountry);
    }

    public void delete(Long id) {
        Country country = countryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + id));

        countryRepo.delete(country);
    }
}
