package ru.otus.library.integration.genre;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.otus.library.app.genre.dto.response.DtoGetGenreResponse;
import ru.otus.library.domain.entities.DbGenre;
import ru.otus.library.integration.BaseSpringTest;
import ru.otus.library.integration.GenericRestClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class GetGenresTest extends BaseSpringTest {

    private class AuthContext {
        List<DbGenre> genres;
        AuthContext(List<DbGenre> genres) {
            this.genres = genres;
        }
    }

    private AuthContext getValidContext() {
        DbGenre genre1 = fixtures.createGenre(UUID.randomUUID().toString());
        DbGenre genre2 = fixtures.createGenre(UUID.randomUUID().toString());
        return new AuthContext(Arrays.asList(genre1, genre2));
    }

    @Test
    public void canGetGenres() {

        AuthContext ctx = getValidContext();

        ResponseEntity<List<DtoGetGenreResponse>> result = new GenericRestClient.Get<DtoGetGenreResponse>()
                .getResultList(createURL("/genres"), new ParameterizedTypeReference<List<DtoGetGenreResponse>>() { });

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().size()).isEqualTo(ctx.genres.size());
    }
}
