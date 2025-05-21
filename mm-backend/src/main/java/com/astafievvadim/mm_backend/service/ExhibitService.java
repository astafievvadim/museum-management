package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Exhibit;
import com.astafievvadim.mm_backend.model.Item;
import com.astafievvadim.mm_backend.repo.ExhibitRepo;
import com.astafievvadim.mm_backend.repo.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExhibitService {

    private final ExhibitRepo exhibitRepo;
    private final ItemRepo itemRepo;

    @Autowired
    public ExhibitService(ExhibitRepo exhibitRepo, ItemRepo itemRepo) {
        this.exhibitRepo = exhibitRepo;
        this.itemRepo = itemRepo;
    }

    public List<Exhibit> findAll() {
        return exhibitRepo.findAll();
    }

    public Exhibit findById(Long id) {
        return exhibitRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Exhibit not found with id: " + id));
    }

    public Exhibit create(Exhibit exhibit) {
        List<Item> items = exhibit.getItems().stream()
                .map(item -> itemRepo.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Item not found with id: " + item.getId())))
                .toList();

        Exhibit newExhibit = new Exhibit();
        newExhibit.setName(exhibit.getName());
        newExhibit.setDescription(exhibit.getDescription());
        newExhibit.setCreationDate(exhibit.getCreationDate());
        newExhibit.setItems(items);

        return exhibitRepo.save(newExhibit);
    }

    public Exhibit update(Exhibit exhibit, Long id) {
        Exhibit existingExhibit = exhibitRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Exhibit not found with id: " + id));

        List<Item> items = exhibit.getItems().stream()
                .map(item -> itemRepo.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Item not found with id: " + item.getId())))
                .toList();

        existingExhibit.setName(exhibit.getName());
        existingExhibit.setDescription(exhibit.getDescription());
        existingExhibit.setCreationDate(exhibit.getCreationDate());
        existingExhibit.setItems(items);

        return exhibitRepo.save(existingExhibit);
    }

    public void delete(Long id) {
        Exhibit exhibit = exhibitRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Exhibit not found with id: " + id));

        exhibitRepo.delete(exhibit);
    }
}
