package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Event;
import com.astafievvadim.mm_backend.model.Gallery;
import com.astafievvadim.mm_backend.model.Partner;
import com.astafievvadim.mm_backend.model.PartnerContribution;
import com.astafievvadim.mm_backend.payload.PartnerContributionDTO;
import com.astafievvadim.mm_backend.repo.EventRepo;
import com.astafievvadim.mm_backend.repo.GalleryRepo;
import com.astafievvadim.mm_backend.repo.PartnerContributionRepo;
import com.astafievvadim.mm_backend.repo.PartnerRepo;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
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
                .orElseThrow(() -> new RuntimeException("PartnerContribution not found with id: " + id));
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
                .orElseThrow(() -> new RuntimeException("PartnerContribution not found with id: " + id));

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
                .orElseThrow(() -> new RuntimeException("PartnerContribution not found with id: " + id));
        contributionRepo.delete(existing);
    }

    // Convert entity to DTO
    public PartnerContributionDTO toDTO(PartnerContribution contribution) {
        if (contribution == null) return null;

        return new PartnerContributionDTO(
                contribution.getId(),
                contribution.getPartner() != null ? contribution.getPartner().getId() : null,
                contribution.getGallery() != null ? contribution.getGallery().getId() : null,
                contribution.getEvent() != null ? contribution.getEvent().getId() : null,
                contribution.getStartDate(),
                contribution.getEndDate()
        );
    }

    // Convert DTO to entity
    public PartnerContribution toEntity(PartnerContributionDTO dto) {
        if (dto == null) return null;

        Partner partner = partnerRepo.findById(dto.getPartnerId())
                .orElseThrow(() -> new RuntimeException("Partner not found with id: " + dto.getPartnerId()));

        Gallery gallery = galleryRepo.findById(dto.getGalleryId())
                .orElseThrow(() -> new RuntimeException("Gallery not found with id: " + dto.getGalleryId()));

        Event event = null;
        if (dto.getEventId() != null) {
            event = eventRepo.findById(dto.getEventId())
                    .orElseThrow(() -> new RuntimeException("Event not found with id: " + dto.getEventId()));
        }

        PartnerContribution contribution = new PartnerContribution();
        contribution.setId(dto.getId());
        contribution.setPartner(partner);
        contribution.setGallery(gallery);
        contribution.setEvent(event);
        contribution.setStartDate(dto.getStartDate());
        contribution.setEndDate(dto.getEndDate());

        return contribution;
    }
}
