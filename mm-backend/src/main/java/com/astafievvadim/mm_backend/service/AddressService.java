package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.*;
import com.astafievvadim.mm_backend.payload.AddressDTO;
import com.astafievvadim.mm_backend.repo.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AddressService {

    private final AddressRepo addressRepo;
    private final CityRepo cityRepo;
    private final CountryRepo countryRepo;
    private final GalleryRepo galleryRepo;
    private final PartnerRepo partnerRepo;
    private final CustomerRepo customerRepo;

    @Autowired
    public AddressService(AddressRepo addressRepo, CityRepo cityRepo, CountryRepo countryRepo,GalleryRepo galleryRepo, PartnerRepo partnerRepo,CustomerRepo customerRepo) {
        this.addressRepo = addressRepo;
        this.partnerRepo = partnerRepo;
        this.cityRepo = cityRepo;
        this.countryRepo = countryRepo;
        this.galleryRepo = galleryRepo;
        this.customerRepo = customerRepo;
    }

    public List<AddressDTO> findAll() {
        return addressRepo.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AddressDTO findById(Long id) {
        Address adr = addressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("address not found with id: " + id));
        return toDTO(adr);
    }

    public Address create(AddressDTO dto) {
        Address address = toEntity(dto);
        return addressRepo.save(address);
    }


    public AddressDTO update(AddressDTO dto, Long id) {
        Address existing = addressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("address not found with id: " + id));

        City city = cityRepo.findById(dto.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found with id: " + dto.getCityId()));

        Country country = countryRepo.findById(dto.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + dto.getCountryId()));

        existing.setStreet(dto.getStreet());
        existing.setCity(city);
        existing.setPostalCode(dto.getPostalCode());

        Address updated = addressRepo.save(existing);
        return toDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        Address address = addressRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        // Nullify address reference in galleries
        List<Gallery> galleries = galleryRepo.findByAddress(address);
        for (Gallery gallery : galleries) {
            gallery.setAddress(null);
        }
        galleryRepo.saveAll(galleries);

        // Nullify address reference in partners
        List<Partner> partners = partnerRepo.findByAddress(address);
        for (Partner partner : partners) {
            partner.setAddress(null);
        }
        partnerRepo.saveAll(partners);

        // Nullify address reference in customers
        List<Customer> customers = customerRepo.findByAddress(address);
        for (Customer customer : customers) {
            customer.setAddress(null);
        }
        customerRepo.saveAll(customers);

        // Now safely delete the address
        addressRepo.delete(address);
    }


    // Mapping from entity to DTO
    public AddressDTO toDTO(Address address) {
        if (address == null) return null;
        return new AddressDTO(
                address.getId(),
                address.getStreet(),
                address.getCity() != null ? address.getCity().getId() : null,
                address.getCity().getCountry() != null ? address.getCity().getCountry().getId() : null,
                address.getPostalCode()
        );
    }

    // Mapping from DTO to entity
    public Address toEntity(AddressDTO dto) {
        if (dto == null) return null;

        City city = cityRepo.findById(dto.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found with id: " + dto.getCityId()));

        Country country = countryRepo.findById(dto.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + dto.getCountryId()));

        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(city);
        address.setPostalCode(dto.getPostalCode());

        return address;
    }
}
