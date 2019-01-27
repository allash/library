package ru.otus.library.integration.book;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.otus.library.app.book.dto.request.DtoCreateCommentRequest;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.entities.DbComment;
import ru.otus.library.domain.repositories.interfaces.BookRepository;
import ru.otus.library.integration.BaseSpringTest;
import ru.otus.library.integration.GenericRestClient;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateCommentTest extends BaseSpringTest {

    @Autowired
    private BookRepository bookRepository;

    public class AuthContext {
        DtoCreateCommentRequest body;
        DbBook book;

        public AuthContext(DtoCreateCommentRequest body, DbBook book) {
            this.body = body;
            this.book = book;
        }
    }

    private AuthContext getValidContext() {
        DbBook book = fixtures.createBook(UUID.randomUUID().toString());

        DtoCreateCommentRequest body = new DtoCreateCommentRequest(UUID.randomUUID().toString());

        return new AuthContext(body, book);
    }

    @Test
    public void canCreateComment() {

        AuthContext ctx = getValidContext();

        ResponseEntity<Object> result = new GenericRestClient.PostOrPut<DtoCreateCommentRequest, Object>()
                .execute(createURL("/books/" + ctx.book.getId() + "/comments"), HttpMethod.POST, ctx.body, Object.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        DbBook bookAfter = bookRepository.findById(ctx.book.getId());
        assertThat(bookAfter.getComments().size()).isEqualTo(1);

        DbComment createdComment = bookAfter.getComments().get(0);
        assertThat(createdComment.getBookId()).isEqualTo(ctx.book.getId());
        assertThat(createdComment.getText()).isEqualTo(ctx.body.getText());
    }

    @Test
    public void cannotCreateCommentWithInvalidBookId() {

        AuthContext ctx = getValidContext();

        ResponseEntity<Object> result = new GenericRestClient.PostOrPut<DtoCreateCommentRequest, Object>()
                .execute(createURL("/books/" + 252352 + "/comments"), HttpMethod.POST, ctx.body, Object.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
