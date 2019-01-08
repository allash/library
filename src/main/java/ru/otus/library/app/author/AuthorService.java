package ru.otus.library.app.author;

import ru.otus.library.app.author.dto.request.DtoCreateOrUpdateAuthorRequest;
import ru.otus.library.app.author.dto.response.DtoGetAuthorBookResponse;
import ru.otus.library.app.author.dto.response.DtoGetAuthorResponse;

import java.util.List;

public interface AuthorService {
    List<DtoGetAuthorResponse> getAuthors();
    DtoGetAuthorResponse getAuthorById(Integer id);
    DtoGetAuthorResponse createAuthor(DtoCreateOrUpdateAuthorRequest dto);
    DtoGetAuthorResponse updateAuthor(Integer id, DtoCreateOrUpdateAuthorRequest dto);
    List<DtoGetAuthorBookResponse> getBooksByAuthorId(Integer id);
}
