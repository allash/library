package ru.otus.library;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ru.otus.library.app.genre.GenreMapper;
import ru.otus.library.app.genre.GenreService;
import ru.otus.library.app.genre.GenreServiceImpl;
import ru.otus.library.app.genre.dto.request.DtoCreateOrUpdateGenreRequest;
import ru.otus.library.app.genre.dto.response.DtoGetGenreResponse;
import ru.otus.library.domain.entities.DbGenre;
import ru.otus.library.domain.repositories.GenreRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class GenreServiceTest {

    private GenreService genreService;

    @Mock
    private GenreRepository genreRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        genreService = new GenreServiceImpl(genreRepository, new GenreMapper());
    }

    @Test
    public void canGetGenres() {
        DbGenre genre1 = new DbGenre(1, "One");
        DbGenre genre2 = new DbGenre(2, "Two");
        List<DbGenre> dbGenres = Stream.of(genre1, genre2).sorted(Comparator.comparing(DbGenre::getId)).collect(Collectors.toList());
        Mockito.when(genreRepository.findAll()).thenReturn(dbGenres);

        List<DtoGetGenreResponse> genres = genreService.getGenres();

        assertThat(genres.size()).isEqualTo(dbGenres.size());

        for (int i = 0; i < dbGenres.size(); i++) {
            assertThat(genres.get(i).getId()).isEqualTo(dbGenres.get(i).getId());
            assertThat(genres.get(i).getName()).isEqualTo(dbGenres.get(i).getName());
        }
    }

    @Test
    public void canCreateGenre() {
        Integer genreId = 1;
        DtoCreateOrUpdateGenreRequest request = new DtoCreateOrUpdateGenreRequest("One");

        Mockito.when(genreRepository.save(any())).thenReturn(new DbGenre(genreId, request.getName()));

        DtoGetGenreResponse response = genreService.createGenre(request);

        assertThat(response.getId()).isEqualTo(genreId);
        assertThat(response.getName()).isEqualTo(request.getName());
    }
}
