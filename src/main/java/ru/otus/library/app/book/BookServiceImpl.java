package ru.otus.library.app.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.library.app.book.dto.request.DtoCreateOrUpdateBookRequest;
import ru.otus.library.app.book.dto.response.DtoGetBookResponse;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.repositories.BookRepository;
import ru.otus.library.domain.repositories.GenreRepository;
import ru.otus.library.shared.exceptions.book.BookNotFoundByIdException;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private GenreRepository genreRepository;
    private BookMapper mapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           GenreRepository genreRepository,
                           BookMapper mapper) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.mapper = mapper;
    }

    @Override
    public List<DtoGetBookResponse> getBooks() {
        return mapper.toList(bookRepository.findAll());
    }

    @Override
    public DtoGetBookResponse getBookById(Integer id) {

        DbBook book = bookRepository.findById(id);
        if (book == null) throw new BookNotFoundByIdException(id);

        return mapper.toDto(book);
    }

    @Override
    public DtoGetBookResponse createBook(DtoCreateOrUpdateBookRequest dto) {
        DbBook book = new DbBook(dto.getTitle());
        book.setGenres(genreRepository.findByIdIn(dto.getGenreIds()));
        return mapper.toDto(bookRepository.save(book));
    }

    @Override
    public DtoGetBookResponse updateBook(Integer id, DtoCreateOrUpdateBookRequest dto) {

        DbBook book = bookRepository.findById(id);
        if (book == null) throw new BookNotFoundByIdException(id);

        book.setTitle(dto.getTitle());

        return mapper.toDto(bookRepository.save(book));
    }
}
