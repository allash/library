package ru.otus.library.app.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.app.comment.dto.request.DtoCreateCommentRequest;
import ru.otus.library.app.comment.dto.response.DtoGetCommentResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{bookId}")
    public List<DtoGetCommentResponse> getCommentsByBookId(@PathVariable Long bookId) {
        return commentService.getCommentsByBookId(bookId);
    }

    @PostMapping("/{bookId}")
    public DtoGetCommentResponse createComment(@PathVariable Long bookId, @Valid @RequestBody DtoCreateCommentRequest body) {
        return commentService.createComment(bookId, body);
    }
}
