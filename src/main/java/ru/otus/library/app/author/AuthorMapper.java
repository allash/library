package ru.otus.library.app.author;

import org.springframework.stereotype.Component;
import ru.otus.library.app.author.dto.response.DtoGetAuthorResponse;
import ru.otus.library.domain.entities.DbAuthor;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorMapper {

    public DtoGetAuthorResponse toDto(DbAuthor author) {

        List<DtoGetAuthorResponse.AuthorBook> books = author.getBooks()
                .stream()
                .map(it -> new DtoGetAuthorResponse.AuthorBook(it.getId(), it.getTitle()))
                .collect(Collectors.toList());

        return new DtoGetAuthorResponse(author.getId(), author.getFirstName(), author.getLastName(), books);
    }

    public List<DtoGetAuthorResponse> toList(List<DbAuthor> authors) {
        return authors.stream().map(this::toDto).collect(Collectors.toList());
    }
}
