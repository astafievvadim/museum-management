package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Address;
import com.astafievvadim.mm_backend.model.Partner;
import com.astafievvadim.mm_backend.repo.AddressRepo;
import com.astafievvadim.mm_backend.repo.PartnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerService {

    private final PartnerRepo partnerRepo;
    private final AddressRepo addressRepo;

    @Autowired
    public PartnerService(PartnerRepo partnerRepo, AddressRepo addressRepo) {
        this.partnerRepo = partnerRepo;
        this.addressRepo = addressRepo;
    }

    public List<Partner> findAll() {
        return partnerRepo.findAll();
    }

    public Partner findById(Long id) {
        return partnerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found with id: " + id));
    }

    public Partner create(Partner partner) {
        Address address = addressRepo.findById(partner.getAddress().getId())
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + partner.getAddress().getId()));

        Partner newPartner = new Partner();
        newPartner.setName(partner.getName());
        newPartner.setAddress(address);

        return partnerRepo.save(newPartner);
    }

    public Partner update(Partner partner, Long id) {
        Partner existingPartner = partnerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found with id: " + id));

        Address address = addressRepo.findById(partner.getAddress().getId())
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + partner.getAddress().getId()));

        existingPartner.setName(partner.getName());
        existingPartner.setAddress(address);

        return partnerRepo.save(existingPartner);
    }

    public void delete(Long id) {
        Partner partner = partnerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found with id: " + id));
        partnerRepo.delete(partner);
    }
}
