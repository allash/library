package ru.otus.library.unit.repositories;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.library.domain.entities.DbAuthor;
import ru.otus.library.domain.repositories.interfaces.AuthorRepositoryJpa;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AuthorRepositoryJpa authorRepository;

    @Test
    public void canFindAllItems() {

        List<DbAuthor> authorsToSave = Arrays.asList(
                new DbAuthor(UUID.randomUUID().toString(), UUID.randomUUID().toString()),
                new DbAuthor(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        );

        authorsToSave.forEach(g -> {
            entityManager.persist(g);
            entityManager.flush();
        });

        List<DbAuthor> result = authorRepository.findAll();
        assertThat(result.size()).isEqualTo(authorsToSave.size());
    }

    @Test
    public void canFindById() {

        DbAuthor author = new DbAuthor(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        entityManager.persist(author);
        entityManager.flush();

        Optional<DbAuthor> authorFound = authorRepository.findById(1L);
        assertThat(authorFound.isPresent()).isTrue();
        assertThat(authorFound.get().getFirstName()).isEqualTo(author.getFirstName());
        assertThat(authorFound.get().getLastName()).isEqualTo(author.getLastName());
    }

    @Test
    public void canCreateNewItem() {
        DbAuthor author = new DbAuthor(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        authorRepository.save(author);

        List<DbAuthor> authors = authorRepository.findAll();
        assertThat(authors.size()).isEqualTo(1);
        assertThat(authors.get(0).getFirstName()).isEqualTo(author.getFirstName());
        assertThat(authors.get(0).getLastName()).isEqualTo(author.getLastName());
    }
}
