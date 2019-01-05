package ru.otus.library;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ru.otus.library.app.author.AuthorMapper;
import ru.otus.library.app.author.AuthorService;
import ru.otus.library.app.author.AuthorServiceImpl;
import ru.otus.library.app.author.dto.request.DtoCreateOrUpdateAuthorRequest;
import ru.otus.library.app.author.dto.response.DtoGetAuthorResponse;
import ru.otus.library.domain.entities.DbAuthor;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.repositories.AuthorRepository;
import ru.otus.library.domain.repositories.BookRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceTest {

    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        authorService = new AuthorServiceImpl(authorRepository, bookRepository, new AuthorMapper());
    }

    @Test
    public void canGetAuthors() {
        DbBook book1 = new DbBook(1, "bla");
        DbBook book2 = new DbBook(2, "bla2");

        DbAuthor author1 = new DbAuthor(1, UUID.randomUUID().toString(), UUID.randomUUID().toString(), Arrays.asList(book1, book2));
        DbAuthor author2 = new DbAuthor(1, UUID.randomUUID().toString(), UUID.randomUUID().toString(), Collections.singletonList(book1));
        List<DbAuthor> authors = Stream.of(author1, author2).sorted(Comparator.comparing(DbAuthor::getId)).collect(Collectors.toList());
        Mockito.when(authorRepository.findAll()).thenReturn(authors);

        List<DtoGetAuthorResponse> response = authorService.getAuthors();
        assertThat(response.size()).isEqualTo(authors.size());

        for (int i = 0; i < response.size(); i++) {
            assertThat(response.get(i).getId()).isEqualTo(authors.get(i).getId());
            assertThat(response.get(i).getFirstName()).isEqualTo(authors.get(i).getFirstName());
            assertThat(response.get(i).getLastName()).isEqualTo(authors.get(i).getLastName());
            assertThat(response.get(i).getBooks().size()).isEqualTo(authors.get(i).getBooks().size());
        }
    }

    @Test
    public void canCreateAuthor() {
        Integer id = 1;
        DbBook book1 = new DbBook(1, "bla");
        DbBook book2 = new DbBook(2, "bla2");
        List<DbBook> books = Arrays.asList(book1, book2);

        DtoCreateOrUpdateAuthorRequest request = new DtoCreateOrUpdateAuthorRequest(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        DbAuthor createdAuthor = new DbAuthor(id, request.getFirstName(), request.getLastName(), books);
        Mockito.when(bookRepository.findByIdIn(any())).thenReturn(books);
        Mockito.when(authorRepository.save(any())).thenReturn(createdAuthor);

        DtoGetAuthorResponse response = authorService.createAuthor(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(id);
        assertThat(response.getFirstName()).isEqualTo(request.getFirstName());
        assertThat(response.getLastName()).isEqualTo(request.getLastName());
        assertThat(response.getBooks().size()).isEqualTo(books.size());
    }
}
