package ru.otus.library;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ru.otus.library.app.book.BookMapper;
import ru.otus.library.app.book.BookService;
import ru.otus.library.app.book.BookServiceImpl;
import ru.otus.library.app.book.dto.request.DtoCreateOrUpdateBookRequest;
import ru.otus.library.app.book.dto.response.DtoGetBookResponse;
import ru.otus.library.domain.entities.DbAuthor;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.entities.DbGenre;
import ru.otus.library.domain.repositories.BookRepository;
import ru.otus.library.domain.repositories.GenreRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private GenreRepository genreRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        bookService = new BookServiceImpl(bookRepository, genreRepository, new BookMapper());
    }

    @Test
    public void canGetBooks() {
        DbGenre genre1 = new DbGenre(1, "One");
        DbGenre genre2 = new DbGenre(2, "Two");

        DbAuthor author1 = new DbAuthor(1, "John", "Doe");
        List<DbAuthor> dbAuthors = Collections.singletonList(author1);

        DbBook book1 = new DbBook(1, "Bla", Arrays.asList(genre1, genre2), dbAuthors);
        DbBook book2 = new DbBook(2, "Bla2", Collections.singletonList(genre1), dbAuthors);
        List<DbBook> dbBooks = Stream.of(book1, book2).sorted(Comparator.comparing(DbBook::getId)).collect(Collectors.toList());

        Mockito.when(bookRepository.findAll()).thenReturn(dbBooks);

        List<DtoGetBookResponse> responses = bookService.getBooks();

        assertThat(responses.size()).isEqualTo(dbBooks.size());

        for (int i = 0; i < responses.size(); i++) {
            assertThat(responses.get(i).getId()).isEqualTo(dbBooks.get(i).getId());
            assertThat(responses.get(i).getTitle()).isEqualTo(dbBooks.get(i).getTitle());
            assertThat(responses.get(i).getGenres().size()).isEqualTo(dbBooks.get(i).getGenres().size());
            assertThat(responses.get(i).getAuthors().size()).isEqualTo(dbBooks.get(i).getAuthors().size());
        }
    }

    @Test
    public void canCreateBook() {
        DbGenre genre1 = new DbGenre(1, "One");
        DbGenre genre2 = new DbGenre(2, "Two");
        List<DbGenre> dbGenres = Arrays.asList(genre1, genre2);

        DbAuthor author1 = new DbAuthor(1, "John", "Doe");
        List<DbAuthor> dbAuthors = Collections.singletonList(author1);

        Integer id = 1;
        DtoCreateOrUpdateBookRequest request = new DtoCreateOrUpdateBookRequest("Bla", Arrays.asList(1, 2));

        DbBook createdBook = new DbBook(id, request.getTitle(), dbGenres, dbAuthors);

        Mockito.when(genreRepository.findByIdIn(any())).thenReturn(dbGenres);
        Mockito.when(bookRepository.save(any())).thenReturn(createdBook);

        DtoGetBookResponse response = bookService.createBook(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(id);
        assertThat(response.getTitle()).isEqualTo(request.getTitle());
        assertThat(response.getGenres().size()).isEqualTo(dbGenres.size());
        assertThat(response.getAuthors().size()).isEqualTo(dbAuthors.size());
    }
}
