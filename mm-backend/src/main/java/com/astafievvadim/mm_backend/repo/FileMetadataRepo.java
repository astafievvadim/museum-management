package com.astafievvadim.mm_backend.repo;

import com.astafievvadim.mm_backend.model.FileMetadata;
import com.astafievvadim.mm_backend.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FileMetadataRepo extends JpaRepository<FileMetadata, Long> {

    List<FileMetadata> findByItemId(Long itemid);
    FileMetadata findByFilename(String filename);

    FileMetadata findByItem(Item item);
}