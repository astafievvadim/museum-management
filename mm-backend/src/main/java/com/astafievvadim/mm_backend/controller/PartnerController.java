package com.astafievvadim.mm_backend.controller;

import com.astafievvadim.mm_backend.model.Partner;
import com.astafievvadim.mm_backend.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
public class PartnerController {

    private final PartnerService partnerService;

    @Autowired
    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @GetMapping
    public List<Partner> getAllPartners() {
        return partnerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partner> getPartnerById(@PathVariable Long id) {
        Partner partner = partnerService.findById(id);
        return ResponseEntity.ok(partner);
    }

    @PostMapping
    public ResponseEntity<Partner> createPartner(@RequestBody Partner partner) {
        Partner createdPartner = partnerService.create(partner);
        return ResponseEntity.ok(createdPartner);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partner> updatePartner(@RequestBody Partner partner, @PathVariable Long id) {
        Partner updatedPartner = partnerService.update(partner, id);
        return ResponseEntity.ok(updatedPartner);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        partnerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
