package ru.otus.library.app.book;

import org.springframework.stereotype.Component;
import ru.otus.library.app.book.dto.response.DtoGetBookCommentResponse;
import ru.otus.library.app.book.dto.response.DtoGetBookResponse;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.entities.DbComment;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public DtoGetBookResponse toDto(DbBook book) {

        List<DtoGetBookResponse.DtoGetBookGenre> genres = book.getGenres()
                .stream()
                .map(it -> new DtoGetBookResponse.DtoGetBookGenre(it.getId(), it.getName()))
                .collect(Collectors.toList());

        List<DtoGetBookResponse.DtoGetBookAuthor> authors = book.getAuthors()
                .stream()
                .map(it -> new DtoGetBookResponse.DtoGetBookAuthor(it.getId(), it.getFirstName(), it.getLastName()))
                .collect(Collectors.toList());

        return new DtoGetBookResponse(book.getId(), book.getTitle(), genres, authors);
    }

    public List<DtoGetBookResponse> toList(List<DbBook> books) {
        return books.stream().map(this::toDto).collect(Collectors.toList());
    }

    public DtoGetBookCommentResponse toCommentDto(DbComment comment) {
        return new DtoGetBookCommentResponse(comment.getId(), comment.getText());
    }

    public List<DtoGetBookCommentResponse> toCommentsList(List<DbComment> comments) {
        return comments.stream().map(this::toCommentDto).collect(Collectors.toList());
    }
}
