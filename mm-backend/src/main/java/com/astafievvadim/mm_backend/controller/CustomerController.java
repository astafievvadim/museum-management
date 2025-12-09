package com.astafievvadim.mm_backend.controller;

import com.astafievvadim.mm_backend.model.Customer;
import com.astafievvadim.mm_backend.service.CustomerService;
import com.astafievvadim.mm_backend.security.CustomUserDetails; // your user details class
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Keep admin or internal access if you want to list all customers, otherwise remove or secure it
    @GetMapping
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_TICKET_AGENT")
    public List<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    // Helper method to get current logged-in user ID
    private Long getCurrentUserId() {
        UsernamePasswordAuthenticationToken auth =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userDetails.getId();  // Adjust if your CustomUserDetails exposes ID differently
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        Long currentUserId = getCurrentUserId();

        if (!id.equals(currentUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }

        Customer customer = customerService.findById(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_TICKET_AGENT")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_TICKET_AGENT")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {
        Long currentUserId = getCurrentUserId();

        if (!id.equals(currentUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }

        Customer existingCustomer = customerService.findById(id);
        if (existingCustomer == null) {
            return ResponseEntity.notFound().build();
        }

        // Update only allowed fields
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setEmail(customer.getEmail()); // if email is allowed to update

        Customer updatedCustomer = customerService.update(existingCustomer, id);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        // Optional: restrict delete to only the logged in user or admins
        customerService.delete(id);
    }
}
