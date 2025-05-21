package com.astafievvadim.mm_backend.controller;

import com.astafievvadim.mm_backend.model.Exhibit;
import com.astafievvadim.mm_backend.service.ExhibitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exhibits")
public class ExhibitController {

    private final ExhibitService exhibitService;

    @Autowired
    public ExhibitController(ExhibitService exhibitService) {
        this.exhibitService = exhibitService;
    }

    @GetMapping
    public List<Exhibit> getAllExhibits() {
        return exhibitService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exhibit> getExhibitById(@PathVariable Long id) {
        Exhibit exhibit = exhibitService.findById(id);
        return ResponseEntity.ok(exhibit);
    }

    @PostMapping
    public ResponseEntity<Exhibit> createExhibit(@RequestBody Exhibit exhibit) {
        Exhibit createdExhibit = exhibitService.create(exhibit);
        return ResponseEntity.ok(createdExhibit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exhibit> updateExhibit(@RequestBody Exhibit exhibit, @PathVariable Long id) {
        Exhibit updatedExhibit = exhibitService.update(exhibit, id);
        return ResponseEntity.ok(updatedExhibit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExhibit(@PathVariable Long id) {
        exhibitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
