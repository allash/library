package ru.otus.library.config;

import org.springframework.stereotype.Component;
import ru.otus.library.domain.entities.DbAuthor;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.entities.DbGenre;
import ru.otus.library.domain.repositories.AuthorRepository;
import ru.otus.library.domain.repositories.BookRepository;
import ru.otus.library.domain.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class FixtureGenerator {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private GenreRepository genreRepository;

    public FixtureGenerator(AuthorRepository authorRepository, BookRepository bookRepository, GenreRepository genreRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
    }

    public DbAuthor createAuthor(String firstName, String lastName) {
        return authorRepository.save(new DbAuthor(firstName, lastName));
    }

    public DbBook createBook(String title) {
        return createBook(title, new ArrayList<>(), new ArrayList<>());
    }

    public DbBook createBook(String title, List<DbGenre> genres, List<DbAuthor> authors) {
        return bookRepository.save(new DbBook(title, genres, authors)); }

    public DbGenre createGenre(String name) {
        return genreRepository.save(new DbGenre(name));
    }
}
