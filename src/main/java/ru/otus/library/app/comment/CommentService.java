package ru.otus.library.app.comment;

import ru.otus.library.app.comment.dto.request.DtoCreateCommentRequest;
import ru.otus.library.app.comment.dto.response.DtoGetCommentResponse;
import ru.otus.library.app.shared.BaseService;

import java.util.List;

public interface CommentService extends BaseService {
    List<DtoGetCommentResponse> getCommentsByBookId(Long id);
    DtoGetCommentResponse createComment(Long bookId, DtoCreateCommentRequest dto);
}
