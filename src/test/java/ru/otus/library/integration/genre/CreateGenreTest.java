package ru.otus.library.integration.genre;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.otus.library.app.genre.dto.request.DtoCreateOrUpdateGenreRequest;
import ru.otus.library.domain.entities.DbGenre;
import ru.otus.library.domain.repositories.interfaces.GenreRepository;
import ru.otus.library.integration.BaseSpringTest;
import ru.otus.library.integration.GenericRestClient;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateGenreTest extends BaseSpringTest {

    @Autowired
    private GenreRepository genreRepository;

    private class AuthContext {
        DtoCreateOrUpdateGenreRequest body;
        AuthContext(DtoCreateOrUpdateGenreRequest body) {
            this.body = body;
        }
    }

    private AuthContext getValidContext() {
        DtoCreateOrUpdateGenreRequest body = new DtoCreateOrUpdateGenreRequest(
                UUID.randomUUID().toString()
        );
        return new AuthContext(body);
    }

    @Test
    public void canCreateNewGenre() {

        List<DbGenre> dbGenresBefore = genreRepository.findAll();
        assertThat(dbGenresBefore.size()).isEqualTo(0);

        AuthContext ctx = getValidContext();

        new GenericRestClient.PostOrPut<DtoCreateOrUpdateGenreRequest, Object>()
                .execute(createURL("/genres"), HttpMethod.POST, ctx.body, Object.class);

        List<DbGenre> dbGenresAfter = genreRepository.findAll();
        assertThat(dbGenresAfter.size()).isEqualTo(1);

        DbGenre created = dbGenresAfter.get(0);
        assertThat(created.getName()).isEqualTo(ctx.body.getName());
    }

    @Test
    public void cannotCreateGenreWithEmptyName() {

        AuthContext ctx = getValidContext();
        ctx.body.setName(null);

        ResponseEntity<Object> result = new GenericRestClient.PostOrPut<DtoCreateOrUpdateGenreRequest, Object>()
                .execute(createURL("/authors"), HttpMethod.POST, ctx.body, Object.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
