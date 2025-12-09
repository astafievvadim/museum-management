package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Event;
import com.astafievvadim.mm_backend.model.Hall;
import com.astafievvadim.mm_backend.payload.EventDTO;
import com.astafievvadim.mm_backend.repo.EventRepo;
import com.astafievvadim.mm_backend.repo.HallRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class EventService {

    private final EventRepo eventRepo;
    private final HallRepo hallRepo;

    @Autowired
    public EventService(EventRepo eventRepo, HallRepo hallRepo) {
        this.eventRepo = eventRepo;
        this.hallRepo = hallRepo;
    }

    public List<Event> findAll() {
        return eventRepo.findAll();
    }

    public Event findById(Long id) {
        return eventRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
    }

    public Event create(Event event) {
        Hall hall = null;
        if (event.getHall() != null && event.getHall().getId() != null) {
            hall = hallRepo.findById(event.getHall().getId())
                    .orElseThrow(() -> new RuntimeException("Hall not found with id: " + event.getHall().getId()));
        }

        Event newEvent = new Event();
        newEvent.setName(event.getName());
        newEvent.setDescription(event.getDescription());
        newEvent.setStartDate(event.getStartDate());
        newEvent.setEndDate(event.getEndDate());
        newEvent.setCapacity(event.getCapacity());
        newEvent.setHall(hall);

        return eventRepo.save(newEvent);
    }

    public Event update(Event event, Long id) {
        Event existingEvent = eventRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        Hall hall = hallRepo.findById(event.getHall().getId())
                .orElseThrow(() -> new RuntimeException("Hall not found with id: " + event.getHall().getId()));

        existingEvent.setName(event.getName());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setStartDate(event.getStartDate());
        existingEvent.setEndDate(event.getEndDate());
        existingEvent.setCapacity(event.getCapacity());
        existingEvent.setHall(hall);

        return eventRepo.save(existingEvent);
    }

    public void delete(Long id) {
        Event event = eventRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        eventRepo.delete(event);
    }

    // Convert Event entity to EventDTO
    public EventDTO toDTO(Event event) {
        if (event == null) return null;

        Long hallId = event.getHall() != null ? event.getHall().getId() : null;

        return new EventDTO(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getStartDate(),
                event.getEndDate(),
                event.getCapacity(),
                hallId
        );
    }

    // Convert EventDTO to Event entity
    public Event toEntity(EventDTO eventDTO) {
        if (eventDTO == null) return null;

        Hall hall = null;
        if (eventDTO.getHallId() != null) {
            hall = hallRepo.findById(eventDTO.getHallId())
                    .orElseThrow(() -> new RuntimeException("Hall not found with id: " + eventDTO.getHallId()));
        }

        Event event = new Event();
        event.setId(eventDTO.getId()); // useful for updates
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setStartDate(eventDTO.getStartDate());
        event.setEndDate(eventDTO.getEndDate());
        event.setCapacity(eventDTO.getCapacity());
        event.setHall(hall);

        return event;
    }
}
