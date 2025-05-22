package com.astafievvadim.mm_backend.security;

import com.astafievvadim.mm_backend.model.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomerUserDetails implements CustomUserDetails {

    private final Customer customer;

    public CustomerUserDetails(Customer customer) {
        this.customer = customer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Customers may have a default role
        return Collections.singleton(() -> "ROLE_CUSTOMER");
    }

    @Override
    public String getPassword() {
        return null; // no password if you do 2FA only or handle differently
    }

    @Override
    public String getUsername() {
        return customer.getEmail();
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
        return customer.getId();
    }

    public String getEmail() {
        return customer.getEmail();
    }

    public Customer getCustomer() {
        return customer;
    }
}
