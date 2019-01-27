package ru.otus.library.integration.book;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.otus.library.app.book.dto.request.DtoCreateOrUpdateBookRequest;
import ru.otus.library.domain.entities.DbAuthor;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.entities.DbGenre;
import ru.otus.library.domain.repositories.interfaces.BookRepository;
import ru.otus.library.integration.BaseSpringTest;
import ru.otus.library.integration.GenericRestClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateBookTest extends BaseSpringTest {

    @Autowired
    private BookRepository bookRepository;

    private class AuthContext {
        DtoCreateOrUpdateBookRequest body;
        List<DbGenre> genres;
        List<DbAuthor> authors;
        AuthContext(DtoCreateOrUpdateBookRequest body, List<DbGenre> genres, List<DbAuthor> authors) {
            this.body = body;
            this.genres = genres;
            this.authors = authors;
        }
    }

    private AuthContext getValidContext() {

        List<DbGenre> genres = Arrays.asList(
                fixtures.createGenre(UUID.randomUUID().toString()),
                fixtures.createGenre(UUID.randomUUID().toString())
        );

        List<DbAuthor> authors = Arrays.asList(
                fixtures.createAuthor(UUID.randomUUID().toString(), UUID.randomUUID().toString()),
                fixtures.createAuthor(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        );

        DtoCreateOrUpdateBookRequest body = new DtoCreateOrUpdateBookRequest(
                UUID.randomUUID().toString(),
                genres.stream().map(DbGenre::getId).collect(Collectors.toList()),
                authors.stream().map(DbAuthor::getId).collect(Collectors.toList())

        );
        return new AuthContext(body, genres, authors);
    }

    @Test
    public void canCreateNewBook() {

        List<DbBook> dbBooksBefore = bookRepository.findAll();
        assertThat(dbBooksBefore.size()).isEqualTo(0);

        AuthContext ctx = getValidContext();

        new GenericRestClient.PostOrPut<DtoCreateOrUpdateBookRequest, Object>()
                .execute(createURL("/books"), HttpMethod.POST, ctx.body, Object.class);

        List<DbBook> dbBooksAfter = bookRepository.findAll();
        assertThat(dbBooksAfter.size()).isEqualTo(1);

        DbBook created = dbBooksAfter.get(0);
        assertThat(created.getTitle()).isEqualTo(ctx.body.getTitle());
    }

    @Test
    public void cannotCreateBookWithEmptyTitle() {

        AuthContext ctx = getValidContext();
        ctx.body.setTitle(null);

        ResponseEntity<Object> result = new GenericRestClient.PostOrPut<DtoCreateOrUpdateBookRequest, Object>()
                .execute(createURL("/books"), HttpMethod.POST, ctx.body, Object.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
