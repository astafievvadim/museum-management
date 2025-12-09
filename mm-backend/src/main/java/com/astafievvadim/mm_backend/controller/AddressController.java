package com.astafievvadim.mm_backend.controller;

import com.astafievvadim.mm_backend.model.Address;
import com.astafievvadim.mm_backend.payload.AddressDTO;
import com.astafievvadim.mm_backend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public List<AddressDTO> getAllAddresses() {
        return addressService.findAll();
    }

    @GetMapping("/{id}")
    public AddressDTO getAddressById(@PathVariable Long id) {
        return addressService.findById(id);
    }

    @PostMapping
    public Address createAddress(@RequestBody AddressDTO addressDTO) {
        return addressService.create(addressDTO);
    }

    @PutMapping("/{id}")
    public AddressDTO updateAddress(@RequestBody AddressDTO addressDTO, @PathVariable Long id) {
        return addressService.update(addressDTO, id);
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Long id) {
        addressService.delete(id);
    }
}
