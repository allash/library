package ru.otus.library.app.author;

import ru.otus.library.app.author.dto.request.DtoCreateOrUpdateAuthorRequest;
import ru.otus.library.app.author.dto.response.DtoGetAuthorBookResponse;
import ru.otus.library.app.author.dto.response.DtoGetAuthorResponse;
import ru.otus.library.app.shared.BaseService;

import java.util.List;

public interface AuthorService extends BaseService {
    List<DtoGetAuthorResponse> getAuthors();
    DtoGetAuthorResponse getAuthorById(Long id);
    DtoGetAuthorResponse createAuthor(DtoCreateOrUpdateAuthorRequest dto);
    DtoGetAuthorResponse updateAuthor(Long id, DtoCreateOrUpdateAuthorRequest dto);
    List<DtoGetAuthorBookResponse> getBooksByAuthorId(Long id);
}
