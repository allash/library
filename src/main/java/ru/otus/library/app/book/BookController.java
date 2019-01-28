package ru.otus.library.app.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.app.book.dto.request.DtoCreateOrUpdateBookRequest;
import ru.otus.library.app.book.dto.response.DtoGetBookResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<DtoGetBookResponse> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/{id}")
    public DtoGetBookResponse getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DtoGetBookResponse createBook(@Valid @RequestBody DtoCreateOrUpdateBookRequest dto) {
        return bookService.createBook(dto);
    }

    @PutMapping("/id")
    public DtoGetBookResponse updateBook(@PathVariable Long id, @Valid @RequestBody DtoCreateOrUpdateBookRequest dto) {
        return bookService.updateBook(id, dto);
    }

   /* @GetMapping("/{bookId}/comments")
    public List<DtoGetBookCommentResponse> getCommentsByBookId(@PathVariable Long bookId) {
        return bookService.getCommentsByBookId(bookId);
    }

    @PostMapping("/{bookId}/comments")
    public DtoGetBookCommentResponse createComment(@PathVariable Long bookId, @Valid @RequestBody DtoCreateCommentRequest body) {
        return bookService.createComment(bookId, body);
    }*/
}
