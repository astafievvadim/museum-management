package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Ticket;
import com.astafievvadim.mm_backend.repo.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepo ticketRepo;

    @Autowired
    public TicketService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public List<Ticket> findAll() {
        return ticketRepo.findAll();
    }

    public Ticket findById(Long id) {
        return ticketRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
    }

    public Ticket create(Ticket ticket) {
        return ticketRepo.save(ticket);
    }

    public Ticket update(Ticket ticket, Long id) {
        Ticket existing = ticketRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));

        existing.setPurchaseDate(ticket.getPurchaseDate());
        existing.setCustomer(ticket.getCustomer());
        existing.setEvent(ticket.getEvent());
        existing.setTicketType(ticket.getTicketType());
        existing.setEmployee(ticket.getEmployee());
        existing.setValid(ticket.isValid());

        return ticketRepo.save(existing);
    }

    public void delete(Long id) {
        Ticket existing = ticketRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
        ticketRepo.delete(existing);
    }
}
