package ru.otus.library.app.book;

import ru.otus.library.app.book.dto.request.DtoCreateOrUpdateBookRequest;
import ru.otus.library.app.book.dto.response.DtoGetBookResponse;
import ru.otus.library.app.shared.BaseService;

import java.util.List;

public interface BookService extends BaseService {
    List<DtoGetBookResponse> getBooks();
    DtoGetBookResponse getBookById(Long id);
    DtoGetBookResponse createBook(DtoCreateOrUpdateBookRequest dto);
    DtoGetBookResponse updateBook(Long id, DtoCreateOrUpdateBookRequest dto);
}
