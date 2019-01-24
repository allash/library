package ru.otus.library.app.genre;

import ru.otus.library.app.genre.dto.request.DtoCreateOrUpdateGenreRequest;
import ru.otus.library.app.genre.dto.response.DtoGetGenreResponse;

import java.util.List;

public interface GenreService {
    List<DtoGetGenreResponse> getGenres();
    DtoGetGenreResponse getGenreById(Integer id);
    DtoGetGenreResponse createGenre(DtoCreateOrUpdateGenreRequest dto);
    DtoGetGenreResponse updateGenre(Integer id, DtoCreateOrUpdateGenreRequest dto);
}
