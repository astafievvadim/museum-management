package com.astafievvadim.mm_backend.controller;

import com.astafievvadim.mm_backend.model.City;
import com.astafievvadim.mm_backend.payload.CityDTO;
import com.astafievvadim.mm_backend.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public List<CityDTO> getAllCities() {
        return cityService.findAll();
    }

    @GetMapping("/{id}")
    public CityDTO getCityById(@PathVariable Long id) {
        return cityService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public City createCity(@RequestBody City city, @RequestParam Long countryId) {
        return cityService.create(city, countryId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public CityDTO updateCity(@RequestBody City city, @PathVariable Long id) {
        return cityService.update(city, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public void deleteCity(@PathVariable Long id) {
        cityService.delete(id);
    }
}
