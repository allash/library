package ru.otus.library.app.author;

import org.springframework.stereotype.Component;
import ru.otus.library.app.author.dto.response.DtoGetAuthorBookResponse;
import ru.otus.library.app.author.dto.response.DtoGetAuthorResponse;
import ru.otus.library.domain.entities.DbAuthor;
import ru.otus.library.domain.entities.DbBook;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorMapper {

    DtoGetAuthorResponse toDto(DbAuthor author) {
        return new DtoGetAuthorResponse(author.getId(), author.getFirstName(), author.getLastName());
    }

    List<DtoGetAuthorResponse> toList(List<DbAuthor> authors) {
        return authors.stream().map(this::toDto).collect(Collectors.toList());
    }

    List<DtoGetAuthorBookResponse> toDto(List<DbBook> books) {
        return books.stream().map(it -> new DtoGetAuthorBookResponse(it.getId(), it.getTitle())).collect(Collectors.toList());
    }
}
