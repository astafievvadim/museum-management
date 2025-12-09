package com.astafievvadim.mm_backend.controller;

import com.astafievvadim.mm_backend.model.Event;
import com.astafievvadim.mm_backend.payload.EventDTO;
import com.astafievvadim.mm_backend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventDTO> getAllEvents() {
        return eventService.findAll().stream()
                .map(eventService::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        Event event = eventService.findById(id);
        return ResponseEntity.ok(eventService.toDTO(event));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
        Event createdEvent = eventService.create(eventService.toEntity(eventDTO));
        return ResponseEntity.ok(eventService.toDTO(createdEvent));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<EventDTO> updateEvent(@RequestBody EventDTO eventDTO, @PathVariable Long id) {
        Event updatedEvent = eventService.update(eventService.toEntity(eventDTO), id);
        return ResponseEntity.ok(eventService.toDTO(updatedEvent));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
