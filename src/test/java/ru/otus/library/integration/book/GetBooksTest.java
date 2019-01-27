package ru.otus.library.integration.book;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.otus.library.app.book.dto.response.DtoGetBookResponse;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.integration.BaseSpringTest;
import ru.otus.library.integration.GenericRestClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBooksTest extends BaseSpringTest {

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
    public void canGetBooks() {

        AuthContext ctx = getValidContext();

        ResponseEntity<List<DtoGetBookResponse>> result = new GenericRestClient.Get<DtoGetBookResponse>()
                .getResultList(createURL("/books"), new ParameterizedTypeReference<List<DtoGetBookResponse>>() { });

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().size()).isEqualTo(ctx.books.size());
    }
}
