package ru.otus.library.shell;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.app.author.dto.request.DtoCreateOrUpdateAuthorRequest;
import ru.otus.library.app.author.dto.response.DtoGetAuthorResponse;
import ru.otus.library.app.book.dto.request.DtoCreateOrUpdateBookRequest;
import ru.otus.library.app.book.dto.response.DtoGetBookResponse;
import ru.otus.library.app.genre.dto.request.DtoCreateOrUpdateGenreRequest;
import ru.otus.library.app.genre.dto.response.DtoGetGenreResponse;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class LibraryShell extends BaseShell {

    @ShellMethod(value = "Get books", key = "getbooks")
    public String getBooks() {

        List<DtoGetBookResponse> books = getResultList("/books", new ParameterizedTypeReference<List<DtoGetBookResponse>>() {});

        return books
                .stream()
                .map(it -> it.getId() + " - " + it.getTitle() + "\n\t" +
                        it.getGenres()
                                .stream()
                                .map(genre -> genre.getId() + " - " + genre.getName())
                                .collect(Collectors.joining("\n\t")))
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Create book", key = "createbook")
    public String createBook(@ShellOption String title, @ShellOption String genreIdsStr) {

        List<Integer> genreIds = Arrays.stream(genreIdsStr.split(","))
                .map(it -> Integer.parseInt(it.trim()))
                .collect(Collectors.toList());

        DtoCreateOrUpdateBookRequest request = new DtoCreateOrUpdateBookRequest(title, genreIds);

        DtoGetBookResponse response = postRequest("/books", request, new ParameterizedTypeReference<DtoGetBookResponse>() { });

        return "New book created: " + response.getId() + " - " + response.getTitle();
    }

    @ShellMethod(value = "Get authors", key = "getauthors")
    public String getAuthors() {

        List<DtoGetAuthorResponse> authors = getResultList("/authors", new ParameterizedTypeReference<List<DtoGetAuthorResponse>>() {});

        return authors
                .stream()
                .map(it -> it.getId() + " - " + it.getFirstName() + ", " + it.getLastName() + "\n\t" +
                        it.getBooks()
                                .stream()
                                .map(book -> book.getId() + " - " + book.getTitle())
                                .collect(Collectors.joining("\n\t")))
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Create author", key = "createauthor")
    public String createAuthor(@ShellOption String firstName, @ShellOption String lastName, @ShellOption String booksIdsStr) {

        List<Integer> bookIds = Arrays.stream(booksIdsStr.split(","))
                .map(it -> Integer.parseInt(it.trim()))
                .collect(Collectors.toList());

        DtoCreateOrUpdateAuthorRequest request = new DtoCreateOrUpdateAuthorRequest(firstName, lastName, bookIds);

        DtoGetAuthorResponse response = postRequest("/authors", request, new ParameterizedTypeReference<DtoGetAuthorResponse>() { });

        return "New author created: " + response.getId() + " - " + response.getFirstName() + ", " + response.getLastName();
    }

    @ShellMethod(value = "Get genres", key = "getgenres")
    public String getGenres() {
        List<DtoGetGenreResponse> genres = getResultList("/genres", new ParameterizedTypeReference<List<DtoGetGenreResponse>>() {});

        return genres
                .stream()
                .map(it -> it.getId() + " - " + it.getName())
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Create genre", key = "creategenre")
    public String createGenre(@ShellOption String name) {

        DtoCreateOrUpdateGenreRequest request = new DtoCreateOrUpdateGenreRequest(name);

        DtoGetGenreResponse response = postRequest("/genres", request, new ParameterizedTypeReference<DtoGetGenreResponse>() {});

        return "New genre created: " + response.getId() + " - " + response.getName();
    }
}
