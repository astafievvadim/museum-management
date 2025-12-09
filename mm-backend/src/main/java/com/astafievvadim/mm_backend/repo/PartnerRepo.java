package com.astafievvadim.mm_backend.repo;

import com.astafievvadim.mm_backend.model.Address;
import com.astafievvadim.mm_backend.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepo extends JpaRepository<Partner,Long> {
    List<Partner> findByAddress(Address address);
}
