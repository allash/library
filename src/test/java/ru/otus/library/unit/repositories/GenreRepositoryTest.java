package ru.otus.library.unit.repositories;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.library.domain.entities.DbGenre;
import ru.otus.library.domain.repositories.interfaces.GenreRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class GenreRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void canFindAllItems() {

        List<DbGenre> genresToSave = Arrays.asList(
                new DbGenre(UUID.randomUUID().toString()),
                new DbGenre(UUID.randomUUID().toString())
        );

        genresToSave.forEach(g -> {
            entityManager.persist(g);
            entityManager.flush();
        });

        List<DbGenre> result = genreRepository.findAll();
        assertThat(result.size()).isEqualTo(genresToSave.size());
    }

    @Test
    public void canFindById() {

        DbGenre genre = new DbGenre(UUID.randomUUID().toString());

        entityManager.persist(genre);
        entityManager.flush();

        Optional<DbGenre> genreFound = genreRepository.findById(1L);
        assertThat(genreFound.isPresent()).isTrue();
        assertThat(genreFound.get().getName()).isEqualTo(genre.getName());
    }

    @Test
    public void canCreateNewItem() {
        DbGenre genre = new DbGenre(UUID.randomUUID().toString());

        genreRepository.save(genre);

        List<DbGenre> genres = genreRepository.findAll();
        assertThat(genres.size()).isEqualTo(1);
        assertThat(genres.get(0).getName()).isEqualTo(genre.getName());
    }
}
