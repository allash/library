package ru.otus.library.integration.comment;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.otus.library.app.comment.dto.request.DtoCreateCommentRequest;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.entities.DbComment;
import ru.otus.library.domain.repositories.interfaces.old.CommentRepository;
import ru.otus.library.integration.BaseSpringTest;
import ru.otus.library.integration.GenericRestClient;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateCommentTest extends BaseSpringTest {

    @Autowired
    private CommentRepository commentRepository;

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
                .execute(createURL("/comments/" + ctx.book.getId()), HttpMethod.POST, ctx.body, Object.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<DbComment> comments = commentRepository.findAllByBookId(ctx.book.getId());
        assertThat(comments.size()).isEqualTo(1);

        DbComment createdComment = comments.get(0);
        assertThat(createdComment.getBookId()).isEqualTo(ctx.book.getId());
        assertThat(createdComment.getText()).isEqualTo(ctx.body.getText());
    }

    @Test
    public void cannotCreateCommentWithInvalidBookId() {

        AuthContext ctx = getValidContext();

        ResponseEntity<Object> result = new GenericRestClient.PostOrPut<DtoCreateCommentRequest, Object>()
                .execute(createURL("/comments/" + 252352), HttpMethod.POST, ctx.body, Object.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
