package com.astafievvadim.mm_backend.repo;

import com.astafievvadim.mm_backend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {

    Employee findByEmail(String email);
}
