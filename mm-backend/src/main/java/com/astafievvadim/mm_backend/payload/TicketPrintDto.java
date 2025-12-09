package com.astafievvadim.mm_backend.payload;

import com.astafievvadim.mm_backend.model.Ticket;

import java.math.BigDecimal;

public class TicketPrintDto {

    private String customerName;

    private String customerLastname;

    private String eventName;

    private String eventDate;

    private String purchaseDate;

    private BigDecimal price;

    private String TicketType;

    public TicketPrintDto(Ticket ticket){
        this.customerName = ticket.getCustomer().getFirstName();
        this.customerLastname = ticket.getCustomer().getLastName();
        this.eventName = ticket.getEvent().getName();
        this.price = ticket.getTicketType().getPrice();
        this.TicketType = ticket.getTicketType().getName();
        this.eventDate = String.valueOf(ticket.getEvent().getStartDate());
        this.purchaseDate = String.valueOf(ticket.getPurchaseDate());

    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerLastname() {
        return customerLastname;
    }

    public void setCustomerLastname(String customerLastname) {
        this.customerLastname = customerLastname;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTicketType() {
        return TicketType;
    }

    public void setTicketType(String ticketType) {
        TicketType = ticketType;
    }
}
