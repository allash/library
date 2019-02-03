package ru.otus.library.unit.repositories;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.entities.DbComment;
import ru.otus.library.domain.repositories.interfaces.BookRepositoryJpa;
import ru.otus.library.domain.repositories.interfaces.CommentRepositoryJpa;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommentRepositoryJpa commentRepository;

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Test
    public void canFindAllItems() {

        DbBook book = bookRepositoryJpa.save(new DbBook(UUID.randomUUID().toString()));

        List<DbComment> commentsToSave = Arrays.asList(
                new DbComment(UUID.randomUUID().toString(), book),
                new DbComment(UUID.randomUUID().toString(), book)
        );

        commentsToSave.forEach(g -> {
            entityManager.persist(g);
            entityManager.flush();
        });

        List<DbComment> result = commentRepository.findAll();
        assertThat(result.size()).isEqualTo(commentsToSave.size());
    }

    @Test
    public void canFindById() {
        DbBook book = bookRepositoryJpa.save(new DbBook(UUID.randomUUID().toString()));
        DbComment comment = new DbComment(UUID.randomUUID().toString(), book);

        entityManager.persist(comment);
        entityManager.flush();

        Optional<DbComment> commentFound = commentRepository.findById(1L);
        assertThat(commentFound.isPresent()).isTrue();
        assertThat(commentFound.get().getText()).isEqualTo(comment.getText());
        assertThat(commentFound.get().getBookId()).isEqualTo(book.getId());
    }

    @Test
    public void canCreateNewItem() {
        DbBook book = bookRepositoryJpa.save(new DbBook(UUID.randomUUID().toString()));
        DbComment comment = new DbComment(UUID.randomUUID().toString(), book);

        commentRepository.save(comment);

        List<DbComment> comments = commentRepository.findAll();
        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getText()).isEqualTo(comment.getText());
        assertThat(comments.get(0).getBookId()).isEqualTo(book.getId());
    }
}
