package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.*;
import com.astafievvadim.mm_backend.payload.ItemDTO;
import com.astafievvadim.mm_backend.repo.ExhibitRepo;
import com.astafievvadim.mm_backend.repo.FileMetadataRepo;
import com.astafievvadim.mm_backend.repo.ItemRepo;
import com.astafievvadim.mm_backend.repo.LocationRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ItemService {
    private final ItemRepo itemRepo;
    private final FileMetadataRepo fileMetadataRepo;
    private final ExhibitRepo exhibitRepository;
    private final LocationRepo locationRepo;
    @Autowired
    public ItemService(ItemRepo itemRepo, FileMetadataRepo fileMetadataRepo,ExhibitRepo exhibitRepository, LocationRepo locationRepo) {
        this.itemRepo = itemRepo;
        this.fileMetadataRepo = fileMetadataRepo;
        this.exhibitRepository = exhibitRepository;
        this.locationRepo = locationRepo;
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
        newItem.setFileMetadata(fileMetadata); // can be null

        return itemRepo.save(newItem);
    }

    public Item update(Item item, Long id) {
        Item existingItem = itemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));

        if (item.getFileMetadata() != null && item.getFileMetadata().getId() != null) {
            FileMetadata fileMetadata = fileMetadataRepo.findById(item.getFileMetadata().getId())
                    .orElseThrow(() -> new RuntimeException("FileMetadata not found with id: " + item.getFileMetadata().getId()));
            existingItem.setFileMetadata(fileMetadata);
        } else {
            existingItem.setFileMetadata(existingItem.getFileMetadata());
        }

        existingItem.setName(item.getName());
        existingItem.setDescription(item.getDescription());
        existingItem.setYear(item.getYear());
        existingItem.setType(item.getType());

        return itemRepo.save(existingItem);
    }

    public void delete(Long id) {
        Item item = itemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));

        for (Exhibit exhibit : item.getExhibits()) {
            exhibit.getItems().remove(item);
            exhibitRepository.save(exhibit); // save changes
        }

        for (Location location : locationRepo.getByItem(item)) {
            locationRepo.delete(location);
        }

        itemRepo.delete(item);
    }

    // Convert Item entity to DTO
    public ItemDTO toDTO(Item item) {
        if (item == null) return null;

        Long fileMetadataId = item.getFileMetadata() != null ? item.getFileMetadata().getId() : null;
        String typeString = item.getType() != null ? item.getType().name() : null; // enum to String

        return new ItemDTO(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getYear(),
                typeString,
                fileMetadataId,
                item.getAuthor().getId()
        );
    }

    // in toEntity()
    public Item toEntity(ItemDTO dto) {
        if (dto == null) return null;

        Item item = new Item();
        item.setId(dto.getId());
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setYear(dto.getYear());

        if (dto.getType() != null) {
            try {
                item.setType(ItemTypeEnum.valueOf(dto.getType())); // String to enum
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid ItemType: " + dto.getType());
            }
        } else {
            item.setType(null);
        }

        if (dto.getFileMetadataId() != null) {
            FileMetadata fileMetadata = fileMetadataRepo.findById(dto.getFileMetadataId())
                    .orElseThrow(() -> new RuntimeException("FileMetadata not found with id: " + dto.getFileMetadataId()));
            item.setFileMetadata(fileMetadata);
        } else {
            item.setFileMetadata(null);
        }

        return item;
    }
}
