package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.City;
import com.astafievvadim.mm_backend.model.Country;
import com.astafievvadim.mm_backend.payload.CityDTO;
import com.astafievvadim.mm_backend.repo.CityRepo;
import com.astafievvadim.mm_backend.repo.CountryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CityService {

    private final CityRepo cityRepo;
    private final CountryRepo countryRepo;

    @Autowired
    public CityService(CityRepo cityRepo, CountryRepo countryRepo) {
        this.cityRepo = cityRepo;
        this.countryRepo = countryRepo;
    }

    // Convert City entity to CityDTO
    public CityDTO toDTO(City city) {
        if (city == null) return null;
        return new CityDTO(
                city.getId(),
                city.getName(),
                city.getPostalCode(),
                city.getCountry().getId()
        );
    }

    // Convert CityDTO to City entity
    public City toEntity(CityDTO cityDTO) {
        if (cityDTO == null) return null;

        City city = new City();
        city.setName(cityDTO.getName());
        city.setPostalCode(cityDTO.getPostalCode());

        if (cityDTO.getCountryId() != null) {
            Country country = countryRepo.findById(cityDTO.getCountryId())
                    .orElseThrow(() -> new RuntimeException("Country not found with id: " + cityDTO.getCountryId()));
            city.setCountry(country);
        } else {
            city.setCountry(null);
        }

        // Addresses typically not set from DTO to entity in this method
        // as they might require separate handling

        return city;
    }

    public List<CityDTO> findAll() {
        List<City> cities = cityRepo.findAll();
        return cities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CityDTO findById(Long id) {
        City city = cityRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found with id: " + id));
        return toDTO(city);
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

    public CityDTO update(City city, Long id) {
        City existingCity = cityRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found with id: " + id));

        if (city.getCountry() != null && city.getCountry().getId() != null) {
            Country country = countryRepo.findById(city.getCountry().getId())
                    .orElseThrow(() -> new RuntimeException("Country not found with id: " + city.getCountry().getId()));
            existingCity.setCountry(country);
        }

        existingCity.setName(city.getName());
        existingCity.setPostalCode(city.getPostalCode());

        City updatedCity = cityRepo.save(existingCity);
        return toDTO(updatedCity);
    }

    public void delete(Long id) {
        City city = cityRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found with id: " + id));
        cityRepo.delete(city);
    }
}
