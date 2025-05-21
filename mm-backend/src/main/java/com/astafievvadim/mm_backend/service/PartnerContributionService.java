package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Event;
import com.astafievvadim.mm_backend.model.Gallery;
import com.astafievvadim.mm_backend.model.Partner;
import com.astafievvadim.mm_backend.model.PartnerContribution;

import com.astafievvadim.mm_backend.repo.EventRepo;
import com.astafievvadim.mm_backend.repo.GalleryRepo;
import com.astafievvadim.mm_backend.repo.PartnerContributionRepo;
import com.astafievvadim.mm_backend.repo.PartnerRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerContributionService {

    private final PartnerContributionRepo contributionRepo;
    private final PartnerRepo partnerRepo;
    private final GalleryRepo galleryRepo;
    private final EventRepo eventRepo;

    @Autowired
    public PartnerContributionService(
            PartnerContributionRepo contributionRepo,
            PartnerRepo partnerRepo,
            GalleryRepo galleryRepo,
            EventRepo eventRepo) {
        this.contributionRepo = contributionRepo;
        this.partnerRepo = partnerRepo;
        this.galleryRepo = galleryRepo;
        this.eventRepo = eventRepo;
    }

    public List<PartnerContribution> findAll() {
        return contributionRepo.findAll();
    }

    public PartnerContribution findById(Long id) {
        return contributionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("PartnerContributionRepo not found with id: " + id));
    }

    public PartnerContribution create(PartnerContribution contribution) {
        Partner partner = partnerRepo.findById(contribution.getPartner().getId())
                .orElseThrow(() -> new RuntimeException("Partner not found with id: " + contribution.getPartner().getId()));

        Gallery gallery = galleryRepo.findById(contribution.getGallery().getId())
                .orElseThrow(() -> new RuntimeException("Gallery not found with id: " + contribution.getGallery().getId()));

        Event event = null;
        if (contribution.getEvent() != null && contribution.getEvent().getId() != null) {
            event = eventRepo.findById(contribution.getEvent().getId())
                    .orElseThrow(() -> new RuntimeException("Event not found with id: " + contribution.getEvent().getId()));
        }

        PartnerContribution newContribution = new PartnerContribution();
        newContribution.setPartner(partner);
        newContribution.setGallery(gallery);
        newContribution.setEvent(event);
        newContribution.setStartDate(contribution.getStartDate());
        newContribution.setEndDate(contribution.getEndDate());

        return contributionRepo.save(newContribution);
    }

    public PartnerContribution update(PartnerContribution contribution, Long id) {
        PartnerContribution existing = contributionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("PartnerContributionRepo not found with id: " + id));

        Partner partner = partnerRepo.findById(contribution.getPartner().getId())
                .orElseThrow(() -> new RuntimeException("Partner not found with id: " + contribution.getPartner().getId()));

        Gallery gallery = galleryRepo.findById(contribution.getGallery().getId())
                .orElseThrow(() -> new RuntimeException("Gallery not found with id: " + contribution.getGallery().getId()));

        Event event = null;
        if (contribution.getEvent() != null && contribution.getEvent().getId() != null) {
            event = eventRepo.findById(contribution.getEvent().getId())
                    .orElseThrow(() -> new RuntimeException("Event not found with id: " + contribution.getEvent().getId()));
        }

        existing.setPartner(partner);
        existing.setGallery(gallery);
        existing.setEvent(event);
        existing.setStartDate(contribution.getStartDate());
        existing.setEndDate(contribution.getEndDate());

        return contributionRepo.save(existing);
    }

    public void delete(Long id) {
        PartnerContribution existing = contributionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("PartnerContributionRepo not found with id: " + id));
        contributionRepo.delete(existing);
    }
}
