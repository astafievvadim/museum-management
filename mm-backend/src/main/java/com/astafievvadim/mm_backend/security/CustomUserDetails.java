package com.astafievvadim.mm_backend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public interface CustomUserDetails extends UserDetails {
    Long getId();
    String getEmail();
}
