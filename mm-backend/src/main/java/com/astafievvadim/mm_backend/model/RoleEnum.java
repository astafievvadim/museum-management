package com.astafievvadim.mm_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


public enum RoleEnum {

    TICKET_AGENT,
    MANAGER,
    ADMIN;

}
