package ru.otus.library.config;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.entities.DbAuthor;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.entities.DbComment;
import ru.otus.library.domain.entities.DbGenre;
import ru.otus.library.domain.repositories.interfaces.old.AuthorRepository;
import ru.otus.library.domain.repositories.interfaces.old.BookRepository;
import ru.otus.library.domain.repositories.interfaces.old.CommentRepository;
import ru.otus.library.domain.repositories.interfaces.old.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class FixtureGenerator {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private GenreRepository genreRepository;
    private CommentRepository commentRepository;

    public FixtureGenerator(
            AuthorRepository authorRepository,
            BookRepository bookRepository,
            GenreRepository genreRepository,
            CommentRepository commentRepository
    ) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    public DbAuthor createAuthor(String firstName, String lastName) {
        return authorRepository.save(new DbAuthor(firstName, lastName));
    }

    public DbBook createBook(String title) {
        return createBook(title, new ArrayList<>(), new ArrayList<>());
    }

    public DbBook createBook(String title, List<DbGenre> genres, List<DbAuthor> authors) {
        return bookRepository.save(new DbBook(title, genres, authors));
    }

    public DbGenre createGenre(String name) {
        return genreRepository.save(new DbGenre(name));
    }

    public DbComment createComment(DbBook book, String text) {
        DbComment comment = new DbComment();
        comment.setText(text);
        comment.setBook(book);
        return commentRepository.save(comment);
    }
}
