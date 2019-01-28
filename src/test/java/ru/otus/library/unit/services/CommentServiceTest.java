package ru.otus.library.unit.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ru.otus.library.app.comment.CommentMapper;
import ru.otus.library.app.comment.CommentService;
import ru.otus.library.app.comment.CommentServiceImpl;
import ru.otus.library.app.comment.dto.request.DtoCreateCommentRequest;
import ru.otus.library.app.comment.dto.response.DtoGetCommentResponse;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.entities.DbComment;
import ru.otus.library.domain.repositories.interfaces.BookRepository;
import ru.otus.library.domain.repositories.interfaces.CommentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

    private CommentService commentService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CommentRepository commentRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        commentService = new CommentServiceImpl(bookRepository, commentRepository, new CommentMapper());
    }

    @Test
    public void canGetComments() {
        Long bookId = 1L;

        DbComment comment1 = new DbComment(1L, UUID.randomUUID().toString());
        DbComment comment2 = new DbComment(1L, UUID.randomUUID().toString());
        List<DbComment> dbComments = Arrays.asList(comment1, comment2);

        Mockito.when(commentRepository.findAllByBookId(bookId)).thenReturn(dbComments);

        List<DtoGetCommentResponse> response = commentService.getCommentsByBookId(bookId);

        assertThat(response.size()).isEqualTo(dbComments.size());
        for (int i = 0; i < response.size(); i++) {
            assertThat(response.get(i).getId()).isEqualTo(dbComments.get(i).getId());
            assertThat(response.get(i).getText()).isEqualTo(dbComments.get(i).getText());
        }
    }

    @Test
    public void canCreateComment() {
        Long bookId = 1L;
        Long commentId = 1L;
        DbBook book = new DbBook(1L, UUID.randomUUID().toString());
        DtoCreateCommentRequest request = new DtoCreateCommentRequest(UUID.randomUUID().toString());

        Mockito.when(bookRepository.findById(bookId)).thenReturn(book);
        Mockito.when(commentRepository.save(any())).thenReturn(new DbComment(commentId, request.getText()));

        DtoGetCommentResponse response = commentService.createComment(bookId, request);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(commentId);
        assertThat(response.getText()).isEqualTo(request.getText());
    }
}
