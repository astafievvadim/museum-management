package com.astafievvadim.mm_backend.controller;

import com.astafievvadim.mm_backend.model.City;
import com.astafievvadim.mm_backend.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public List<City> getAllCities() {
        return cityService.findAll();
    }

    @GetMapping("/{id}")
    public City getCityById(@PathVariable Long id) {
        return cityService.findById(id);
    }

    @PostMapping
    public City createCity(@RequestBody City city, @RequestParam Long countryId) {
        return cityService.create(city, countryId);
    }

    @PutMapping("/{id}")
    public City updateCity(@RequestBody City city, @PathVariable Long id) {
        return cityService.update(city, id);
    }

    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable Long id) {
        cityService.delete(id);
    }
}
