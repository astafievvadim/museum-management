package com.astafievvadim.mm_backend.model;

import jakarta.persistence.*;

import java.util.Date;
@Entity
public class Ticket {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    private Date purchaseDate;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Person customer;

    @ManyToOne
    @JoinColumn(name = "eventId")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "ticketTypeId")
    private TicketType ticketType;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Person employee;

    private boolean isValid;

    public Ticket() {
    }

    public Ticket(Date purchaseDate, Person customer, Event event, TicketType ticketType, Person employee, boolean isValid) {
        this.purchaseDate = purchaseDate;
        this.customer = customer;
        this.event = event;
        this.ticketType = ticketType;
        this.employee = employee;
        this.isValid = isValid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Person getCustomer() {
        return customer;
    }

    public void setCustomer(Person customer) {
        this.customer = customer;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public Person getEmployee() {
        return employee;
    }

    public void setEmployee(Person employee) {
        this.employee = employee;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
