package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Event;
import com.astafievvadim.mm_backend.model.Hall;
import com.astafievvadim.mm_backend.repo.EventRepo;
import com.astafievvadim.mm_backend.repo.HallRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
}
