package ru.otus.library.integration.author;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.otus.library.app.author.dto.response.DtoGetAuthorResponse;
import ru.otus.library.domain.entities.DbAuthor;
import ru.otus.library.integration.BaseSpringTest;
import ru.otus.library.integration.GenericRestClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class GetAuthorsTest extends BaseSpringTest {

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
    public void canGetAuthors() {

        AuthContext ctx = getValidContext();

        ResponseEntity<List<DtoGetAuthorResponse>> result = new GenericRestClient.Get<DtoGetAuthorResponse>()
                .getResultList(createURL("/authors"), new ParameterizedTypeReference<List<DtoGetAuthorResponse>>() { });

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().size()).isEqualTo(ctx.authors.size());
    }
}
