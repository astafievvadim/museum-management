package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Employee;
import com.astafievvadim.mm_backend.model.RoleEnum;
import com.astafievvadim.mm_backend.payload.EmployeeDTO;
import com.astafievvadim.mm_backend.repo.EmployeeRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    public Employee findById(Long id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    public Employee create(Employee employee) {
        RoleEnum role = employee.getRole();

        Employee newEmployee = new Employee();
        newEmployee.setFirstName(employee.getFirstName());
        newEmployee.setLastName(employee.getLastName());
        newEmployee.setBirthdate(employee.getBirthdate());
        newEmployee.setEmail(employee.getEmail());
        newEmployee.setRole(role);

        return employeeRepo.save(newEmployee);
    }

    public Employee update(Employee employee, Long id) {
        Employee existing = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        RoleEnum role = employee.getRole();

        existing.setFirstName(employee.getFirstName());
        existing.setLastName(employee.getLastName());
        existing.setBirthdate(employee.getBirthdate());
        existing.setEmail(employee.getEmail());
        existing.setRole(role);

        return employeeRepo.save(existing);
    }

    public void delete(Long id) {
        Employee existing = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employeeRepo.delete(existing);
    }

    // Convert Employee entity to EmployeeDTO
    public EmployeeDTO toDTO(Employee employee) {
        if (employee == null) return null;

        return new EmployeeDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getBirthdate(),
                employee.getEmail(),
                employee.getRole()
        );
    }

    // Convert EmployeeDTO to Employee entity
    public Employee toEntity(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) return null;

        Employee employee = new Employee();
        employee.setId(employeeDTO.getId()); // optional, useful for updates
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setBirthdate(employeeDTO.getBirthdate());
        employee.setEmail(employeeDTO.getEmail());
        employee.setRole(employeeDTO.getRole());

        return employee;
    }
}
