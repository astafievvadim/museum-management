package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Customer;
import com.astafievvadim.mm_backend.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;

    @Autowired
    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    public Customer findById(Long id) {
        return customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    public Customer create(Customer customer) {
        return customerRepo.save(customer);
    }

    public Customer update(Customer customer, Long id) {
        Customer existing = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        existing.setFirstName(customer.getFirstName());
        existing.setLastName(customer.getLastName());
        existing.setBirthdate(customer.getBirthdate());
        existing.setEmail(customer.getEmail());
        existing.setTelegramId(customer.getTelegramId());
        existing.setTelegramUserId(customer.getTelegramUserId());
        existing.setTelegramUsername(customer.getTelegramUsername());

        return customerRepo.save(existing);
    }

    public void delete(Long id) {
        Customer existing = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        customerRepo.delete(existing);
    }
}
