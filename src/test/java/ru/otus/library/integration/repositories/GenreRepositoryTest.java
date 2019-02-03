package ru.otus.library.integration.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.library.domain.entities.DbGenre;
import ru.otus.library.domain.repositories.interfaces.GenreRepositoryJpa;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GenreRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GenreRepositoryJpa genreRepository;

    @Test
    public void whenFindAll_thenReturnList() {

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
}
