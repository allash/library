package ru.otus.library.app.genre;

import org.springframework.stereotype.Component;
import ru.otus.library.app.genre.dto.response.DtoGetGenreResponse;
import ru.otus.library.domain.entities.DbGenre;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenreMapper {

    public DtoGetGenreResponse toDto(DbGenre genre) {
        return new DtoGetGenreResponse(genre.getId(), genre.getName());
    }

    public List<DtoGetGenreResponse> toList(List<DbGenre> genres) {
        return genres.stream().map(this::toDto).collect(Collectors.toList());
    }
}
