package com.astafievvadim.mm_backend.payload;

import java.util.Date;

public class TicketDTO {
    private Date purchaseDate;
    private Long eventId;
    private Long customerId;
    private Long ticketTypeId;
    private Long employeeId;
    private boolean isValid;

    public TicketDTO() {}

    public TicketDTO(Date purchaseDate, Long eventId, Long customerId, Long ticketTypeId, Long employeeId, boolean isValid) {
        this.purchaseDate = purchaseDate;
        this.eventId = eventId;
        this.customerId = customerId;
        this.ticketTypeId = ticketTypeId;
        this.employeeId = employeeId;
        this.isValid = isValid;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getTicketTypeId() {
        return ticketTypeId;
    }

    public void setTicketTypeId(Long ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
