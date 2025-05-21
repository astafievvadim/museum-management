package com.astafievvadim.mm_backend.controller;

import com.astafievvadim.mm_backend.model.PartnerContribution;
import com.astafievvadim.mm_backend.service.PartnerContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partner-contributions")
public class PartnerContributionController {

    private final PartnerContributionService contributionService;

    @Autowired
    public PartnerContributionController(PartnerContributionService contributionService) {
        this.contributionService = contributionService;
    }

    @GetMapping
    public List<PartnerContribution> getAllContributions() {
        return contributionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnerContribution> getContributionById(@PathVariable Long id) {
        PartnerContribution contribution = contributionService.findById(id);
        return ResponseEntity.ok(contribution);
    }

    @PostMapping
    public ResponseEntity<PartnerContribution> createContribution(@RequestBody PartnerContribution contribution) {
        PartnerContribution created = contributionService.create(contribution);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartnerContribution> updateContribution(@RequestBody PartnerContribution contribution, @PathVariable Long id) {
        PartnerContribution updated = contributionService.update(contribution, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContribution(@PathVariable Long id) {
        contributionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
