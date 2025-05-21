package com.astafievvadim.mm_backend.repo;

import com.astafievvadim.mm_backend.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepo extends JpaRepository<Country,Long> {
}
