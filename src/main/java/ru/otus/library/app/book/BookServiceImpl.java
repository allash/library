package ru.otus.library.app.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.library.app.book.dto.request.DtoCreateOrUpdateBookRequest;
import ru.otus.library.app.book.dto.response.DtoGetBookResponse;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.repositories.interfaces.AuthorRepository;
import ru.otus.library.domain.repositories.interfaces.BookRepository;
import ru.otus.library.domain.repositories.interfaces.GenreRepository;
import ru.otus.library.shared.exceptions.book.BookNotFoundByIdException;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private GenreRepository genreRepository;
    private AuthorRepository authorRepository;
    private BookMapper mapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           GenreRepository genreRepository,
                           AuthorRepository authorRepository,
                           BookMapper mapper) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.mapper = mapper;
    }

    @Override
    public List<DtoGetBookResponse> getBooks() {
        return mapper.toList(bookRepository.findAll());
    }

    @Override
    public DtoGetBookResponse getBookById(Long id) {

        Optional<DbBook> book = bookRepository.findById(id);
        if (!book.isPresent()) throw new BookNotFoundByIdException(id);

        return mapper.toDto(book.get());
    }

    @Override
    public DtoGetBookResponse createBook(DtoCreateOrUpdateBookRequest dto) {
        DbBook book = new DbBook(dto.getTitle());
        book.setGenres(genreRepository.findByIdIn(dto.getGenreIds()));
        book.setAuthors(authorRepository.findByIdIn(dto.getAuthorIds()));
        return mapper.toDto(bookRepository.save(book));
    }

    @Override
    public DtoGetBookResponse updateBook(Long id, DtoCreateOrUpdateBookRequest dto) {

        Optional<DbBook> bookOptional = bookRepository.findById(id);
        if (!bookOptional.isPresent()) throw new BookNotFoundByIdException(id);

        DbBook book = bookOptional.get();
        book.setTitle(dto.getTitle());
        book.setGenres(genreRepository.findByIdIn(dto.getGenreIds()));
        book.setAuthors(authorRepository.findByIdIn(dto.getAuthorIds()));

        return mapper.toDto(bookRepository.save(book));
    }
}
