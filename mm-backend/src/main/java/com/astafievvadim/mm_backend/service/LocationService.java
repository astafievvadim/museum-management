package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Event;
import com.astafievvadim.mm_backend.model.Hall;
import com.astafievvadim.mm_backend.model.Item;
import com.astafievvadim.mm_backend.model.Location;
import com.astafievvadim.mm_backend.repo.EventRepo;
import com.astafievvadim.mm_backend.repo.HallRepo;
import com.astafievvadim.mm_backend.repo.ItemRepo;
import com.astafievvadim.mm_backend.repo.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
}
