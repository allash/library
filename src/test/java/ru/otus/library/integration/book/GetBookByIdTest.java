package ru.otus.library.integration.book;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.otus.library.app.book.dto.response.DtoGetBookResponse;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.repositories.interfaces.BookRepository;
import ru.otus.library.integration.BaseSpringTest;
import ru.otus.library.integration.GenericRestClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBookByIdTest extends BaseSpringTest {

    @Autowired
    private BookRepository bookRepository;

    private class AuthContext {
        List<DbBook> books;
        AuthContext(List<DbBook> books) {
            this.books = books;
        }
    }

    private AuthContext getValidContext() {
        DbBook book1 = fixtures.createBook(UUID.randomUUID().toString());
        DbBook book2 = fixtures.createBook(UUID.randomUUID().toString());
        return new AuthContext(Arrays.asList(book1, book2));
    }

    @Test
    public void canGetBookById() {
        AuthContext ctx = getValidContext();

        List<DbBook> createdList = bookRepository.findAll();
        assertThat(createdList.size()).isEqualTo(ctx.books.size());

        DbBook book = ctx.books.get(0);

        ResponseEntity<DtoGetBookResponse> result = new GenericRestClient.Get<DtoGetBookResponse>()
                .getResult(createURL("/books/" + book.getId()), DtoGetBookResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(result).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(book.getId());
        assertThat(result.getBody().getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    public void cannotGetBookWithInvalidId() {
        ResponseEntity<DtoGetBookResponse> result = new GenericRestClient.Get<DtoGetBookResponse>()
                .getResult(createURL("/books/" + 33434), DtoGetBookResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
