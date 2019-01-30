package ru.otus.library.app.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.library.app.comment.dto.request.DtoCreateCommentRequest;
import ru.otus.library.app.comment.dto.response.DtoGetCommentResponse;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.entities.DbComment;
import ru.otus.library.domain.repositories.interfaces.BookRepository;
import ru.otus.library.domain.repositories.interfaces.CommentRepository;
import ru.otus.library.shared.exceptions.book.BookNotFoundByIdException;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private BookRepository bookRepository;
    private CommentRepository commentRepository;
    private CommentMapper mapper;

    @Autowired
    public CommentServiceImpl(BookRepository bookRepository, CommentRepository commentRepository, CommentMapper mapper) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    @Override
    public List<DtoGetCommentResponse> getCommentsByBookId(Long bookId) {
        return mapper.toCommentsList(commentRepository.findAllByBookId(bookId));
    }

    @Override
    public DtoGetCommentResponse createComment(Long bookId, DtoCreateCommentRequest dto) {
        DbBook book = bookRepository.findById(bookId);
        if (book == null) throw new BookNotFoundByIdException(bookId);

        DbComment comment = new DbComment();
        comment.setText(dto.getText());
        comment.setBook(book);
        return mapper.toCommentDto(commentRepository.save(comment));
    }
}
