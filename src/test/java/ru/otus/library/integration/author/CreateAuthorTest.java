package ru.otus.library.integration.author;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.otus.library.app.author.dto.request.DtoCreateOrUpdateAuthorRequest;
import ru.otus.library.domain.entities.DbAuthor;
import ru.otus.library.domain.repositories.interfaces.AuthorRepository;
import ru.otus.library.integration.BaseSpringTest;
import ru.otus.library.integration.GenericRestClient;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateAuthorTest extends BaseSpringTest {

    @Autowired
    private AuthorRepository authorRepository;

    private class AuthContext {
        DtoCreateOrUpdateAuthorRequest body;
        AuthContext(DtoCreateOrUpdateAuthorRequest body) {
            this.body = body;
        }
    }

    private AuthContext getValidContext() {
        DtoCreateOrUpdateAuthorRequest body = new DtoCreateOrUpdateAuthorRequest(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
        return new AuthContext(body);
    }

    @Test
    public void canCreateNewAuthor() {

        List<DbAuthor> dbAuthorsBefore= authorRepository.findAll();
        assertThat(dbAuthorsBefore.size()).isEqualTo(0);

        AuthContext ctx = getValidContext();

        new GenericRestClient.PostOrPut<DtoCreateOrUpdateAuthorRequest, Object>()
                .execute(createURL("/authors"), HttpMethod.POST, ctx.body, Object.class);

        List<DbAuthor> dbAuthorsAfter = authorRepository.findAll();
        assertThat(dbAuthorsAfter.size()).isEqualTo(1);

        DbAuthor created = dbAuthorsAfter.get(0);
        assertThat(created.getFirstName()).isEqualTo(ctx.body.getFirstName());
        assertThat(created.getLastName()).isEqualTo(ctx.body.getLastName());
    }

    @Test
    public void cannotCreateAuthorWithEmptyFirstName() {

        AuthContext ctx = getValidContext();
        ctx.body.setFirstName(null);

        ResponseEntity<Object> result = new GenericRestClient.PostOrPut<DtoCreateOrUpdateAuthorRequest, Object>()
                .execute(createURL("/authors"), HttpMethod.POST, ctx.body, Object.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void cannotCreateAuthorWithEmptyLastName() {

        AuthContext ctx = getValidContext();
        ctx.body.setLastName(null);

        ResponseEntity<Object> result = new GenericRestClient.PostOrPut<DtoCreateOrUpdateAuthorRequest, Object>()
                .execute(createURL("/authors"), HttpMethod.POST, ctx.body, Object.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
