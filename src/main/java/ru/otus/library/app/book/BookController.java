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
    public DtoGetBookResponse getBookById(@PathVariable Integer id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DtoGetBookResponse createBook(@Valid @RequestBody DtoCreateOrUpdateBookRequest dto) {
        return bookService.createBook(dto);
    }

    @PutMapping("/id")
    public DtoGetBookResponse updateBook(@PathVariable Integer id, @Valid @RequestBody DtoCreateOrUpdateBookRequest dto) {
        return bookService.updateBook(id, dto);
    }
}
