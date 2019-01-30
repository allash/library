package ru.otus.library.app.comment;

import org.springframework.stereotype.Component;
import ru.otus.library.app.comment.dto.response.DtoGetCommentResponse;
import ru.otus.library.domain.entities.DbComment;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {
    public DtoGetCommentResponse toCommentDto(DbComment comment) {
        return new DtoGetCommentResponse(comment.getId(), comment.getText());
    }

    public List<DtoGetCommentResponse> toCommentsList(List<DbComment> comments) {
        return comments.stream().map(this::toCommentDto).collect(Collectors.toList());
    }
}
