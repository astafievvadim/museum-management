package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Event;
import com.astafievvadim.mm_backend.model.Hall;
import com.astafievvadim.mm_backend.model.Item;
import com.astafievvadim.mm_backend.model.Location;
import com.astafievvadim.mm_backend.payload.LocationDTO;
import com.astafievvadim.mm_backend.repo.EventRepo;
import com.astafievvadim.mm_backend.repo.HallRepo;
import com.astafievvadim.mm_backend.repo.ItemRepo;
import com.astafievvadim.mm_backend.repo.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LocationService {

    private final LocationRepo locationRepo;
    private final EventRepo eventRepo;
    private final ItemRepo itemRepo;
    private final HallRepo hallRepo;

    @Autowired
    public LocationService(LocationRepo locationRepo, EventRepo eventRepo, ItemRepo itemRepo, HallRepo hallRepo) {
        this.locationRepo = locationRepo;
        this.eventRepo = eventRepo;
        this.itemRepo = itemRepo;
        this.hallRepo = hallRepo;
    }

    public List<Location> findAll() {
        return locationRepo.findAll();
    }

    public Location findById(Long id) {
        return locationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
    }

    public Location create(Location location) {
        Event event = eventRepo.findById(location.getEvent().getId())
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + location.getEvent().getId()));

        Item item = itemRepo.findById(location.getItem().getId())
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + location.getItem().getId()));

        Hall hall = hallRepo.findById(location.getHall().getId())
                .orElseThrow(() -> new RuntimeException("Hall not found with id: " + location.getHall().getId()));

        Location newLocation = new Location();
        newLocation.setEvent(event);
        newLocation.setItem(item);
        newLocation.setHall(hall);
        newLocation.setPlaceDate(location.getPlaceDate());
        newLocation.setRemoveDate(location.getRemoveDate());

        return locationRepo.save(newLocation);
    }

    public Location update(Location location, Long id) {
        Location existingLocation = locationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));

        Event event = eventRepo.findById(location.getEvent().getId())
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + location.getEvent().getId()));

        Item item = itemRepo.findById(location.getItem().getId())
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + location.getItem().getId()));

        Hall hall = hallRepo.findById(location.getHall().getId())
                .orElseThrow(() -> new RuntimeException("Hall not found with id: " + location.getHall().getId()));

        existingLocation.setEvent(event);
        existingLocation.setItem(item);
        existingLocation.setHall(hall);
        existingLocation.setPlaceDate(location.getPlaceDate());
        existingLocation.setRemoveDate(location.getRemoveDate());

        return locationRepo.save(existingLocation);
    }

    public void delete(Long id) {
        Location location = locationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
        locationRepo.delete(location);
    }

    // Convert entity to DTO
    public LocationDTO toDTO(Location location) {
        if (location == null) return null;

        return new LocationDTO(
                location.getId(),
                location.getEvent() != null ? location.getEvent().getId() : null,
                location.getItem() != null ? location.getItem().getId() : null,
                location.getHall() != null ? location.getHall().getId() : null,
                location.getPlaceDate(),
                location.getRemoveDate()
        );
    }

    // Convert DTO to entity
    public Location toEntity(LocationDTO dto) {
        if (dto == null) return null;

        Event event = eventRepo.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + dto.getEventId()));

        Item item = itemRepo.findById(dto.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + dto.getItemId()));

        Hall hall = hallRepo.findById(dto.getHallId())
                .orElseThrow(() -> new RuntimeException("Hall not found with id: " + dto.getHallId()));

        Location location = new Location();
        location.setId(dto.getId());
        location.setEvent(event);
        location.setItem(item);
        location.setHall(hall);
        location.setPlaceDate(dto.getPlaceDate());
        location.setRemoveDate(dto.getRemoveDate());

        return location;
    }
}
