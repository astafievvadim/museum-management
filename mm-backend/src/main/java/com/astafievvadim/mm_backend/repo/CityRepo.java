package com.astafievvadim.mm_backend.repo;

import com.astafievvadim.mm_backend.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepo extends JpaRepository<City,Long> {
}
