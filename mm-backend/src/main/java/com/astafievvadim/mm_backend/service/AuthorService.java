package com.astafievvadim.mm_backend.service;

import com.astafievvadim.mm_backend.model.Author;
import com.astafievvadim.mm_backend.model.Country;
import com.astafievvadim.mm_backend.model.Item;
import com.astafievvadim.mm_backend.repo.AuthorRepo;
import com.astafievvadim.mm_backend.repo.CountryRepo;
import com.astafievvadim.mm_backend.repo.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepo authorRepo;
    private final CountryRepo countryRepo;
    private final ItemRepo itemRepo;

    @Autowired
    public AuthorService(AuthorRepo authorRepo, CountryRepo countryRepo, ItemRepo itemRepo) {
        this.authorRepo = authorRepo;
        this.countryRepo = countryRepo;
        this.itemRepo = itemRepo;
    }

    public List<Author> findAll() {
        return authorRepo.findAll();
    }

    public Author findById(Long id) {
        return authorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
    }

    public Author create(Author author) {
        Author newAuthor = new Author();

        Country country = countryRepo.findById(author.getCountry().getId())
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + author.getCountry().getId()));

        newAuthor.setName(author.getName());
        newAuthor.setLastname(author.getLastname());
        newAuthor.setBirthDate(author.getBirthDate());
        newAuthor.setDeathDate(author.getDeathDate());
        newAuthor.setCountry(country);

        if (author.getItems() != null && !author.getItems().isEmpty()) {
            List<Item> items = itemRepo.findAllById(
                    author.getItems().stream().map(Item::getId).toList()
            );
            newAuthor.setItems(items);
        }

        return authorRepo.save(newAuthor);
    }

    public Author update(Author author, Long id) {
        Author existingAuthor = authorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));

        Country country = countryRepo.findById(author.getCountry().getId())
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + author.getCountry().getId()));

        existingAuthor.setName(author.getName());
        existingAuthor.setLastname(author.getLastname());
        existingAuthor.setBirthDate(author.getBirthDate());
        existingAuthor.setDeathDate(author.getDeathDate());
        existingAuthor.setCountry(country);

        if (author.getItems() != null && !author.getItems().isEmpty()) {
            List<Item> items = itemRepo.findAllById(
                    author.getItems().stream().map(Item::getId).toList()
            );
            existingAuthor.setItems(items);
        } else {
            existingAuthor.setItems(null);
        }

        return authorRepo.save(existingAuthor);
    }

    public void delete(Long id) {
        Author author = authorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        authorRepo.delete(author);
    }
}
