package ru.otus.library.integration.author;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.otus.library.app.author.dto.response.DtoGetAuthorResponse;
import ru.otus.library.domain.entities.DbAuthor;
import ru.otus.library.domain.repositories.interfaces.old.AuthorRepository;
import ru.otus.library.integration.BaseSpringTest;
import ru.otus.library.integration.GenericRestClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class GetAuthorByIdTest extends BaseSpringTest {

    @Autowired
    private AuthorRepository authorRepository;

    private class AuthContext {
        List<DbAuthor> authors;
        AuthContext(List<DbAuthor> authors) {
            this.authors = authors;
        }
    }

    private AuthContext getValidContext() {
        DbAuthor author1 = fixtures.createAuthor(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        DbAuthor author2 = fixtures.createAuthor(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        return new AuthContext(Arrays.asList(author1, author2));
    }

    @Test
    public void canGetAuthorById() {
        AuthContext ctx = getValidContext();

        List<DbAuthor> createdList = authorRepository.findAll();
        assertThat(createdList.size()).isEqualTo(ctx.authors.size());

        DbAuthor author = ctx.authors.get(0);

        ResponseEntity<DtoGetAuthorResponse> result = new GenericRestClient.Get<DtoGetAuthorResponse>()
                .getResult(createURL("/authors/" + author.getId()), DtoGetAuthorResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(result).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(author.getId());
        assertThat(result.getBody().getFirstName()).isEqualTo(author.getFirstName());
        assertThat(result.getBody().getLastName()).isEqualTo(author.getLastName());
    }

    @Test
    public void cannotGetGenreWithInvalidId() {
        ResponseEntity<DtoGetAuthorResponse> result = new GenericRestClient.Get<DtoGetAuthorResponse>()
                .getResult(createURL("/authors/" + 33434), DtoGetAuthorResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
