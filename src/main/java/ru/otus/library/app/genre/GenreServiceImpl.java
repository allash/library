package ru.otus.library.app.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.library.app.genre.dto.request.DtoCreateOrUpdateGenreRequest;
import ru.otus.library.app.genre.dto.response.DtoGetGenreResponse;
import ru.otus.library.domain.entities.DbGenre;
import ru.otus.library.domain.repositories.interfaces.GenreRepository;
import ru.otus.library.shared.exceptions.genre.GenreNotFoundByIdException;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreRepository genreRepository;
    private GenreMapper mapper;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository, GenreMapper mapper) {
        this.genreRepository = genreRepository;
        this.mapper = mapper;
    }

    @Override
    public List<DtoGetGenreResponse> getGenres() {
        return mapper.toList(genreRepository.findAll());
    }

    @Override
    public DtoGetGenreResponse getGenreById(Long id) {

        Optional<DbGenre> genre = genreRepository.findById(id);
        if (!genre.isPresent()) throw new GenreNotFoundByIdException(id);

        return mapper.toDto(genre.get());
    }

    @Override
    public DtoGetGenreResponse createGenre(DtoCreateOrUpdateGenreRequest dto) {
        return mapper.toDto(genreRepository.save(new DbGenre(dto.getName())));
    }

    @Override
    public DtoGetGenreResponse updateGenre(Long id, DtoCreateOrUpdateGenreRequest dto) {

        Optional<DbGenre> genre = genreRepository.findById(id);
        if (!genre.isPresent()) throw new GenreNotFoundByIdException(id);

        genre.get().setName(dto.getName());

        return mapper.toDto(genreRepository.save(genre.get()));
    }
}
