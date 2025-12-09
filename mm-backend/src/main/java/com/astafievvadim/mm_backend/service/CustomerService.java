package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Customer;
import com.astafievvadim.mm_backend.payload.CustomerDTO;
import com.astafievvadim.mm_backend.payload.CustomerSignUpRequest;
import com.astafievvadim.mm_backend.repo.CustomerRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
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

    // Check if email exists
    public boolean existsByEmail(String email) {
        return customerRepo.findByEmail(email) != null;
    }

    public Customer registerNewCustomer(CustomerSignUpRequest signUpRequest) {
        if (existsByEmail(signUpRequest.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        Customer customer = new Customer();
        customer.setEmail(signUpRequest.getEmail());
        customer.setFirstName(signUpRequest.getFirstName());
        customer.setLastName(signUpRequest.getLastName());

        return customerRepo.save(customer);
    }

    // Convert Customer entity to CustomerDTO
    public CustomerDTO toDTO(Customer customer) {
        if (customer == null) return null;

        return new CustomerDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getBirthdate(),
                customer.getEmail(),
                customer.getTelegramId(),
                customer.getTelegramUserId(),
                customer.getTelegramUsername(),
                customer.getAddress().getId()
        );
    }

    // Convert CustomerDTO to Customer entity
    public Customer toEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) return null;

        Customer customer = new Customer();
        customer.setId(customerDTO.getId()); // optional: for updates
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setBirthdate(customerDTO.getBirthdate());
        customer.setEmail(customerDTO.getEmail());
        customer.setTelegramId(customerDTO.getTelegramId());
        customer.setTelegramUserId(customerDTO.getTelegramUserId());
        customer.setTelegramUsername(customerDTO.getTelegramUsername());

        return customer;
    }
}
