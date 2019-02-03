package ru.otus.library.domain.repositories.impl.jpa;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.entities.DbComment;
import ru.otus.library.domain.repositories.interfaces.old.CommentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Primary
@Repository
public class CommentRepositoryJpaImpl implements CommentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DbComment> findAllByBookId(Long bookId) {
        TypedQuery<DbComment> query = entityManager.createQuery("SELECT c FROM DbComment c WHERE c.bookId = :book_id", DbComment.class);
        query.setParameter("book_id", bookId);
        return query.getResultList();
    }

    @Override
    public DbComment save(DbComment comment) {
        entityManager.persist(comment);
        return comment;
    }
}
