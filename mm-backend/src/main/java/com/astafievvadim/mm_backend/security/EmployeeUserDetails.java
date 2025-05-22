package com.astafievvadim.mm_backend.security;

import com.astafievvadim.mm_backend.model.Employee;
import com.astafievvadim.mm_backend.model.RoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class EmployeeUserDetails implements CustomUserDetails {

    private final Employee employee;

    public EmployeeUserDetails(Employee employee) {
        this.employee = employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Map RoleEnum to authorities
        return Collections.singleton(() -> "ROLE_" + employee.getRole().name());
    }

    @Override
    public String getPassword() {
        return null; // same as above for 2FA
    }

    @Override
    public String getUsername() {
        return employee.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return employee.getId();
    }

    public String getEmail() {
        return employee.getEmail();
    }

    public Employee getEmployee() {
        return employee;
    }
}
