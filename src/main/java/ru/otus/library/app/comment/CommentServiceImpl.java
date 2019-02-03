package ru.otus.library.app.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.library.app.comment.dto.request.DtoCreateCommentRequest;
import ru.otus.library.app.comment.dto.response.DtoGetCommentResponse;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.entities.DbComment;
import ru.otus.library.domain.repositories.interfaces.BookRepositoryJpa;
import ru.otus.library.domain.repositories.interfaces.CommentRepositoryJpa;
import ru.otus.library.shared.exceptions.book.BookNotFoundByIdException;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private BookRepositoryJpa bookRepository;
    private CommentRepositoryJpa commentRepository;
    private CommentMapper mapper;

    @Autowired
    public CommentServiceImpl(BookRepositoryJpa bookRepository, CommentRepositoryJpa commentRepository, CommentMapper mapper) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    @Override
    public List<DtoGetCommentResponse> getCommentsByBookId(Long bookId) {
        return mapper.toCommentsList(commentRepository.findByBookId(bookId));
    }

    @Override
    public DtoGetCommentResponse createComment(Long bookId, DtoCreateCommentRequest dto) {
        Optional<DbBook> book = bookRepository.findById(bookId);
        if (!book.isPresent()) throw new BookNotFoundByIdException(bookId);

        DbComment comment = new DbComment();
        comment.setText(dto.getText());
        comment.setBook(book.get());
        return mapper.toCommentDto(commentRepository.save(comment));
    }
}
