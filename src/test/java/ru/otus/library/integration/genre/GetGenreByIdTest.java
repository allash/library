package ru.otus.library.integration.genre;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.otus.library.app.genre.dto.response.DtoGetGenreResponse;
import ru.otus.library.domain.entities.DbGenre;
import ru.otus.library.domain.repositories.interfaces.old.GenreRepository;
import ru.otus.library.integration.BaseSpringTest;
import ru.otus.library.integration.GenericRestClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class GetGenreByIdTest extends BaseSpringTest {

    @Autowired
    private GenreRepository genreRepository;

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
    public void canGetAuthorById() {
        AuthContext ctx = getValidContext();

        List<DbGenre> createdList = genreRepository.findAll();
        assertThat(createdList.size()).isEqualTo(ctx.genres.size());

        DbGenre genre = ctx.genres.get(0);

        ResponseEntity<DtoGetGenreResponse> result = new GenericRestClient.Get<DtoGetGenreResponse>()
                .getResult(createURL("/genres/" + genre.getId()), DtoGetGenreResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(result).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(genre.getId());
        assertThat(result.getBody().getName()).isEqualTo(genre.getName());
    }

    @Test
    public void cannotGetGenreWithInvalidId() {
        ResponseEntity<DtoGetGenreResponse> result = new GenericRestClient.Get<DtoGetGenreResponse>()
                .getResult(createURL("/genres/" + 33434), DtoGetGenreResponse.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
