package com.astafievvadim.mm_backend.repo;

import com.astafievvadim.mm_backend.model.Item;
import com.astafievvadim.mm_backend.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepo extends JpaRepository<Location,Long> {
    List<Location> getByItem(Item item);
}
