package ru.otus.library.app.genre;

import ru.otus.library.app.genre.dto.request.DtoCreateOrUpdateGenreRequest;
import ru.otus.library.app.genre.dto.response.DtoGetGenreResponse;
import ru.otus.library.app.shared.BaseService;

import java.util.List;

public interface GenreService extends BaseService {
    List<DtoGetGenreResponse> getGenres();
    DtoGetGenreResponse getGenreById(Long id);
    DtoGetGenreResponse createGenre(DtoCreateOrUpdateGenreRequest dto);
    DtoGetGenreResponse updateGenre(Long id, DtoCreateOrUpdateGenreRequest dto);
}
