package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Address;
import com.astafievvadim.mm_backend.model.City;
import com.astafievvadim.mm_backend.model.Country;
import com.astafievvadim.mm_backend.repo.AddressRepo;
import com.astafievvadim.mm_backend.repo.CityRepo;
import com.astafievvadim.mm_backend.repo.CountryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private AddressRepo addressRepo;
    private CityRepo cityRepo;

    private CountryRepo countryRepo;

    @Autowired
    public AddressService(AddressRepo addressRepo, CityRepo cityRepo, CountryRepo countryRepo) {
        this.addressRepo = addressRepo;
        this.cityRepo = cityRepo;
        this.countryRepo = countryRepo;
    }

    public List<Address> findAll(){
        return addressRepo.findAll();
    }

    public Address findById(Long id){

        Address adr = addressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("adress not found with id: " + id));

        return adr;
    }

    public Address create(Address address){

        Address adr = new Address();

        City city = cityRepo.findById(address.getCity().getId())
                .orElseThrow(() -> new RuntimeException("City not found with id: " + address.getCity().getId()));

        Country country = countryRepo.findById(address.getCountry().getId())
                .orElseThrow(() -> new RuntimeException("country not found with id: " + address.getCountry().getId()));

        String street = address.getStreet();

        String postal = address.getPostalCode();

        adr.setCity(city);
        adr.setCountry(country);
        adr.setStreet(street);
        adr.setPostalCode(postal);

        addressRepo.save(adr);

        return adr;
    }

    public Address update(Address address, Long id){

        Address adr = addressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("adress not found with id: " + id));

        City city = cityRepo.findById(address.getCity().getId())
                .orElseThrow(() -> new RuntimeException("City not found with id: " + address.getCity().getId()));

        Country country = countryRepo.findById(address.getCountry().getId())
                .orElseThrow(() -> new RuntimeException("country not found with id: " + address.getCountry().getId()));

        String street = address.getStreet();

        String postal = address.getPostalCode();

        adr.setCity(city);
        adr.setCountry(country);
        adr.setStreet(street);
        adr.setPostalCode(postal);

        addressRepo.save(adr);
        return adr;

    }

    public void delete(Long id){

        Address adr = addressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("adress not found with id: " + id));

        addressRepo.delete(adr);
    }


}
