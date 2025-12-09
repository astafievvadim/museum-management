package com.astafievvadim.mm_backend.controller;

import com.astafievvadim.mm_backend.payload.AuthorDTO;
import com.astafievvadim.mm_backend.model.Author;
import com.astafievvadim.mm_backend.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorDTO> getAllAuthors() {
        return authorService.findAll()
                .stream()
                .map(authorService::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AuthorDTO getAuthorById(@PathVariable Long id) {
        Author author = authorService.findById(id);
        return authorService.toDTO(author);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public AuthorDTO createAuthor(@RequestBody AuthorDTO authorDTO) {
        Author author = authorService.toEntity(authorDTO);
        Author saved = authorService.create(author);
        return authorService.toDTO(saved);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public AuthorDTO updateAuthor(@RequestBody AuthorDTO authorDTO, @PathVariable Long id) {
        Author author = authorService.toEntity(authorDTO);
        Author updated = authorService.update(author, id);
        return authorService.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.delete(id);
    }
}
