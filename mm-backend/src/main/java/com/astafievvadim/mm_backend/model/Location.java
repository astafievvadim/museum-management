package com.astafievvadim.mm_backend.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "eventId")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "itemId")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "hallId")
    private Hall hall;

    @Temporal(TemporalType.TIMESTAMP)
    private Date placeDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date removeDate;

    public Location() {
    }

    public Location(Event event, Item item, Hall hall, Date placeDate, Date removeDate) {
        this.event = event;
        this.item = item;
        this.hall = hall;
        this.placeDate = placeDate;
        this.removeDate = removeDate;
    }

    // Getters and setters below...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Date getPlaceDate() {
        return placeDate;
    }

    public void setPlaceDate(Date placeDate) {
        this.placeDate = placeDate;
    }

    public Date getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(Date removeDate) {
        this.removeDate = removeDate;
    }
}
