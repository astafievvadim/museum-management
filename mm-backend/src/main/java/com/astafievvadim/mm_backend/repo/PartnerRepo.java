package com.astafievvadim.mm_backend.repo;

import com.astafievvadim.mm_backend.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepo extends JpaRepository<Partner,Long> {
}
