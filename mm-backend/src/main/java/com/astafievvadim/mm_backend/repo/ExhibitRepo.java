package com.astafievvadim.mm_backend.repo;

import com.astafievvadim.mm_backend.model.Exhibit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitRepo extends JpaRepository<Exhibit,Long> {
}
