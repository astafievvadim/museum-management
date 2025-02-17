package com.astafievvadim.museum_management.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "exhibits")
public class Exhibit {

    @Id
    @GeneratedValue
    Long id;

    String label;

    Date creation_date;

    @ManyToOne
    Author author;
}
