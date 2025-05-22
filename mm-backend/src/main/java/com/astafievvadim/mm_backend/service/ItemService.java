package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.FileMetadata;
import com.astafievvadim.mm_backend.model.Item;
import com.astafievvadim.mm_backend.repo.FileMetadataRepo;
import com.astafievvadim.mm_backend.repo.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepo itemRepo;
    private final FileMetadataRepo fileMetadataRepo;

    @Autowired
    public ItemService(ItemRepo itemRepo, FileMetadataRepo fileMetadataRepo) {
        this.itemRepo = itemRepo;
        this.fileMetadataRepo = fileMetadataRepo;
    }

    public List<Item> findAll() {
        return itemRepo.findAll();
    }

    public Item findById(Long id) {
        return itemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
    }

    public Item create(Item item) {
        FileMetadata fileMetadata = null;
        if (item.getFileMetadata() != null) {
            fileMetadata = fileMetadataRepo.findById(item.getFileMetadata().getId())
                    .orElseThrow(() -> new RuntimeException("FileMetadata not found with id: " + item.getFileMetadata().getId()));
        }

        Item newItem = new Item();
        newItem.setName(item.getName());
        newItem.setDescription(item.getDescription());
        newItem.setYear(item.getYear());
        newItem.setType(item.getType());
        newItem.setFileMetadata(fileMetadata); // will be null if none provided

        return itemRepo.save(newItem);
    }


    public Item update(Item item, Long id) {
        Item existingItem = itemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));

        FileMetadata fileMetadata = fileMetadataRepo.findById(item.getFileMetadata().getId())
                .orElseThrow(() -> new RuntimeException("FileMetadata not found with id: " + item.getFileMetadata().getId()));

        existingItem.setName(item.getName());
        existingItem.setDescription(item.getDescription());
        existingItem.setYear(item.getYear());
        existingItem.setType(item.getType());
        existingItem.setFileMetadata(fileMetadata);

        return itemRepo.save(existingItem);
    }

    public void delete(Long id) {
        Item item = itemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
        itemRepo.delete(item);
    }
}
