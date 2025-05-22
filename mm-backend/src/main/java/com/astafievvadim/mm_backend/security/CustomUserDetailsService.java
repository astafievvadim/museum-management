package com.astafievvadim.mm_backend.security;

import com.astafievvadim.mm_backend.model.Customer;
import com.astafievvadim.mm_backend.model.Employee;
import com.astafievvadim.mm_backend.repo.CustomerRepo;
import com.astafievvadim.mm_backend.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepo customerRepository;
    private final EmployeeRepo employeeRepository;

    @Autowired
    public CustomUserDetailsService(CustomerRepo customerRepository,
                                 EmployeeRepo employeeRepository) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Try find employee first (or customer first, your choice)
        Employee employee = employeeRepository.findByEmail(email);
        if (employee != null) {
            return new EmployeeUserDetails(employee);
        }

        Customer customer = customerRepository.findByEmail(email);
        if (customer != null) {
            return new CustomerUserDetails(customer);
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }

    public boolean existsByEmail(String email) {
        return customerRepository.findByEmail(email) != null ||
                employeeRepository.findByEmail(email) != null;
    }


    public Optional<Customer> findCustomerByEmail(String email) {
        return Optional.ofNullable(customerRepository.findByEmail(email));
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
}
