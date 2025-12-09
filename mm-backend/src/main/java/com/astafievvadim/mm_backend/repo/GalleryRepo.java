package com.astafievvadim.mm_backend.repo;

import com.astafievvadim.mm_backend.model.Address;
import com.astafievvadim.mm_backend.model.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryRepo extends JpaRepository<Gallery,Long> {
    List<Gallery> findByAddress(Address address);
}
