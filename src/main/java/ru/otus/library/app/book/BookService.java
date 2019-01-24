package ru.otus.library.app.book;

import ru.otus.library.app.book.dto.request.DtoCreateOrUpdateBookRequest;
import ru.otus.library.app.book.dto.response.DtoGetBookResponse;

import java.util.List;

public interface BookService {
    List<DtoGetBookResponse> getBooks();
    DtoGetBookResponse getBookById(Integer id);
    DtoGetBookResponse createBook(DtoCreateOrUpdateBookRequest dto);
    DtoGetBookResponse updateBook(Integer id, DtoCreateOrUpdateBookRequest dto);
}
