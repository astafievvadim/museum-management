package com.astafievvadim.mm_backend.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public enum ItemTypeEnum {

    Painting,
    Book;

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
}
