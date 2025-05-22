package com.astafievvadim.mm_backend.repo;


import com.astafievvadim.mm_backend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Customer findByTelegramUserId(Long telegramUserId);

    Customer findByEmail(String email);
}
