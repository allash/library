package ru.otus.library.integration.book;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.otus.library.app.book.dto.response.DtoGetBookCommentResponse;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.entities.DbComment;
import ru.otus.library.integration.BaseSpringTest;
import ru.otus.library.integration.GenericRestClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class GetCommentsByBookIdTest extends BaseSpringTest {

    private class AuthContext {
        DbBook book;
        List<DbComment> comments;

        public AuthContext(DbBook book, List<DbComment> comments) {
            this.book = book;
            this.comments = comments;
        }
    }

    private AuthContext getValidContext() {
        DbBook book = fixtures.createBook(UUID.randomUUID().toString());
        List<DbComment> comments = Arrays.asList(
                fixtures.createComment(book, UUID.randomUUID().toString()),
                fixtures.createComment(book, UUID.randomUUID().toString()),
                fixtures.createComment(book, UUID.randomUUID().toString()),
                fixtures.createComment(book, UUID.randomUUID().toString())
        );

        return new AuthContext(book, comments);
    }

    @Test
    public void canGetComments() {

        AuthContext ctx = getValidContext();

        ResponseEntity<List<DtoGetBookCommentResponse>> result = new GenericRestClient.Get<DtoGetBookCommentResponse>()
                .getResultList(createURL("/books/" + ctx.book.getId() + "/comments"), new ParameterizedTypeReference<List<DtoGetBookCommentResponse>>() {});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().size()).isEqualTo(ctx.comments.size());
    }
}
