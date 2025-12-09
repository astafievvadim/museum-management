package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Customer;
import com.astafievvadim.mm_backend.model.Event;
import com.astafievvadim.mm_backend.model.Ticket;
import com.astafievvadim.mm_backend.model.TicketType;
import com.astafievvadim.mm_backend.payload.CustomerDTO;
import com.astafievvadim.mm_backend.payload.EventDTO;
import com.astafievvadim.mm_backend.payload.TicketDTO;
import com.astafievvadim.mm_backend.payload.TicketTypeDTO;
import com.astafievvadim.mm_backend.repo.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TicketService {

    @Autowired
    private CustomerRepo customerRepository;

    @Autowired
    private EventRepo eventRepository;

    @Autowired
    private TicketTypeRepo ticketTypeRepository;

    @Autowired
    private EmployeeRepo employeeRepository;


    private final TicketRepo ticketRepo;

    @Autowired
    public TicketService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    @Transactional
    public List<Ticket> findAll() {
        List<Ticket> tickets = ticketRepo.findAll();

        return tickets;
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

    public Ticket toEntity(TicketDTO ticketDTO){

        Ticket ticket = new Ticket();

        ticket.setPurchaseDate(ticketDTO.getPurchaseDate());
        ticket.setValid(ticketDTO.isValid());

        ticket.setCustomer(customerRepository.findById(ticketDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found")));

        ticket.setEvent(eventRepository.findById(ticketDTO.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found")));

        ticket.setTicketType(ticketTypeRepository.findById(ticketDTO.getTicketTypeId())
                .orElseThrow(() -> new RuntimeException("TicketType not found")));

        if (ticketDTO.getEmployeeId() != null) {
            ticket.setEmployee(employeeRepository.findById(ticketDTO.getEmployeeId())
                    .orElse(null)); // employee is optional, so null is allowed
        } else {
            ticket.setEmployee(null);
        }

        return ticket;
    }
}
