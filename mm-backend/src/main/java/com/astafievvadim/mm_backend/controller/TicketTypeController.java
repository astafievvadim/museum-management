package com.astafievvadim.mm_backend.controller;

import com.astafievvadim.mm_backend.model.TicketType;
import com.astafievvadim.mm_backend.service.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket-types")
public class TicketTypeController {

    private final TicketTypeService ticketTypeService;

    @Autowired
    public TicketTypeController(TicketTypeService ticketTypeService) {
        this.ticketTypeService = ticketTypeService;
    }

    @GetMapping
    public List<TicketType> getAllTicketTypes() {
        return ticketTypeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketType> getTicketTypeById(@PathVariable Long id) {
        TicketType ticketType = ticketTypeService.findById(id);
        return ResponseEntity.ok(ticketType);
    }

    @PostMapping
    public ResponseEntity<TicketType> createTicketType(@RequestBody TicketType ticketType) {
        TicketType created = ticketTypeService.create(ticketType);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketType> updateTicketType(@RequestBody TicketType ticketType, @PathVariable Long id) {
        TicketType updated = ticketTypeService.update(ticketType, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicketType(@PathVariable Long id) {
        ticketTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
