package ru.otus.library.unit.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.repositories.interfaces.BookRepositoryJpa;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class BookRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepositoryJpa bookRepository;

    @Test
    public void canFindAllItems() {

        List<DbBook> booksToSave = Arrays.asList(
                new DbBook(UUID.randomUUID().toString()),
                new DbBook(UUID.randomUUID().toString())
        );

        booksToSave.forEach(g -> {
            entityManager.persist(g);
            entityManager.flush();
        });

        List<DbBook> result = bookRepository.findAll();
        assertThat(result.size()).isEqualTo(booksToSave.size());
    }

    @Test
    public void canFindById() {

        DbBook book = new DbBook(UUID.randomUUID().toString());

        entityManager.persist(book);
        entityManager.flush();

        Optional<DbBook> bookFound = bookRepository.findById(1L);
        assertThat(bookFound.isPresent()).isTrue();
        assertThat(bookFound.get().getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    public void canCreateNewItem() {
        DbBook genre = new DbBook(UUID.randomUUID().toString());

        bookRepository.save(genre);

        List<DbBook> books = bookRepository.findAll();
        assertThat(books.size()).isEqualTo(1);
        assertThat(books.get(0).getTitle()).isEqualTo(genre.getTitle());
    }
}
