package com.astafievvadim.mm_backend.controller;

import com.astafievvadim.mm_backend.model.Hall;
import com.astafievvadim.mm_backend.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/halls")
public class HallController {

    private final HallService hallService;

    @Autowired
    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @GetMapping
    public List<Hall> getAllHalls() {
        return hallService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hall> getHallById(@PathVariable Long id) {
        Hall hall = hallService.findById(id);
        return ResponseEntity.ok(hall);
    }

    @PostMapping
    public ResponseEntity<Hall> createHall(@RequestBody Hall hall) {
        Hall createdHall = hallService.create(hall);
        return ResponseEntity.ok(createdHall);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hall> updateHall(@RequestBody Hall hall, @PathVariable Long id) {
        Hall updatedHall = hallService.update(hall, id);
        return ResponseEntity.ok(updatedHall);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHall(@PathVariable Long id) {
        hallService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
