package com.astafievvadim.mm_backend.payload;

import java.util.Date;

public class LocationDTO {

    private Long id;
    private Long eventId;
    private Long itemId;
    private Long hallId;
    private Date placeDate;
    private Date removeDate;

    public LocationDTO() {
    }

    public LocationDTO(Long id, Long eventId, Long itemId, Long hallId, Date placeDate, Date removeDate) {
        this.id = id;
        this.eventId = eventId;
        this.itemId = itemId;
        this.hallId = hallId;
        this.placeDate = placeDate;
        this.removeDate = removeDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
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
