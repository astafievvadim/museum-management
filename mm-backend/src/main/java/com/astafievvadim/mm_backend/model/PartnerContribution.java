package com.astafievvadim.mm_backend.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class PartnerContribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="partnerId")
    private Partner partner;

    @ManyToOne
    @JoinColumn(name="galleryId")
    private Gallery gallery;

    @ManyToOne
    private Event event;

    private Date startDate;
    private Date endDate;

    public PartnerContribution() {
    }

    public PartnerContribution(Partner partner, Gallery gallery, Date startDate, Date endDate, Event event) {
        this.partner = partner;
        this.gallery = gallery;
        this.startDate = startDate;
        this.endDate = endDate;
        this.event = event;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
