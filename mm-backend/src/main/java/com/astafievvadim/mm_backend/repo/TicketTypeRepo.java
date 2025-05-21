package com.astafievvadim.mm_backend.repo;

import com.astafievvadim.mm_backend.model.Ticket;
import com.astafievvadim.mm_backend.model.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketTypeRepo extends JpaRepository<TicketType,Long> {
}
