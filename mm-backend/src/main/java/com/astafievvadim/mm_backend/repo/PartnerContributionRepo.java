package com.astafievvadim.mm_backend.repo;

import com.astafievvadim.mm_backend.model.PartnerContribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerContributionRepo extends JpaRepository<PartnerContribution,Long> {
}
