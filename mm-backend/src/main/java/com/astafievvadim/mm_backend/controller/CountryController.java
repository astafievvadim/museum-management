package com.astafievvadim.mm_backend.controller;

import com.astafievvadim.mm_backend.model.Country;
import com.astafievvadim.mm_backend.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public List<Country> getAllCountries() {
        return countryService.findAll();
    }

    @GetMapping("/{id}")
    public Country getCountryById(@PathVariable Long id) {
        return countryService.findById(id);
    }

    @PostMapping
    public Country createCountry(@RequestBody Country country) {
        return countryService.create(country);
    }

    @PutMapping("/{id}")
    public Country updateCountry(@RequestBody Country country, @PathVariable Long id) {
        return countryService.update(country, id);
    }

    @DeleteMapping("/{id}")
    public void deleteCountry(@PathVariable Long id) {
        countryService.delete(id);
    }
}
