package com.astafievvadim.mm_backend.payload;

import java.util.Date;

public class PartnerContributionDTO {

    private Long id;
    private Long partnerId;
    private Long galleryId;
    private Long eventId;
    private Date startDate;
    private Date endDate;

    public PartnerContributionDTO() {
    }

    public PartnerContributionDTO(Long id, Long partnerId, Long galleryId, Long eventId, Date startDate, Date endDate) {
        this.id = id;
        this.partnerId = partnerId;
        this.galleryId = galleryId;
        this.eventId = eventId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(Long galleryId) {
        this.galleryId = galleryId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
