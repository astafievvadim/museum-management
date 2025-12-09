package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.TicketType;
import com.astafievvadim.mm_backend.payload.TicketTypeDTO;
import com.astafievvadim.mm_backend.repo.TicketTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TicketTypeService {

    private final TicketTypeRepo ticketTypeRepo;

    @Autowired
    public TicketTypeService(TicketTypeRepo ticketTypeRepo) {
        this.ticketTypeRepo = ticketTypeRepo;
    }

    public List<TicketType> findAll() {
        return ticketTypeRepo.findAll();
    }

    public TicketType findById(Long id) {
        return ticketTypeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket type not found with id: " + id));
    }

    public TicketType create(TicketType ticketType) {
        return ticketTypeRepo.save(ticketType);
    }

    public TicketType update(TicketType ticketType, Long id) {
        TicketType existing = ticketTypeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket type not found with id: " + id));

        existing.setName(ticketType.getName());
        existing.setPrice(ticketType.getPrice());

        return ticketTypeRepo.save(existing);
    }

    public void delete(Long id) {
        TicketType existing = ticketTypeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket type not found with id: " + id));
        ticketTypeRepo.delete(existing);
    }

    public TicketTypeDTO toDTO(TicketType ticketType) {
        if (ticketType == null) return null;

        TicketTypeDTO dto = new TicketTypeDTO();
        dto.setId(ticketType.getId());
        dto.setName(ticketType.getName());
        dto.setPrice(ticketType.getPrice());
        return dto;
    }

    public TicketType toEntity(TicketTypeDTO dto) {
        if (dto == null) return null;

        TicketType ticketType = new TicketType();
        ticketType.setId(dto.getId());
        ticketType.setName(dto.getName());
        ticketType.setPrice(dto.getPrice());
        return ticketType;
    }

}
