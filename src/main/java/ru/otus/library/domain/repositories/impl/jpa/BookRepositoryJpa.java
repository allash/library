package ru.otus.library.domain.repositories.impl.jpa;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.repositories.interfaces.BookRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Primary
@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long count() {
        return (long) entityManager.createQuery("SELECT COUNT(b) FROM DbBook b").getSingleResult();
    }

    @Override
    public DbBook findById(Long id) {
        return entityManager.find(DbBook.class, id);
    }

    @Override
    public List<DbBook> findAll() {
        TypedQuery<DbBook> query = entityManager.createQuery("SELECT b FROM DbBook b", DbBook.class);
        return query.getResultList();
    }

    @Override
    public DbBook save(DbBook entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<DbBook> findByIdIn(Collection<Long> collection) {
        Map namedParams = Collections.singletonMap("ids", collection);
        TypedQuery<DbBook> query = entityManager.createQuery("SELECT b FROM DbBook b WHERE b.id IN (:ids)", DbBook.class);
        query.setParameter("ids", namedParams);
        return query.getResultList();
    }

    @Override
    public List<DbBook> findByAuthorId(Long authorId) {
        String sql = "SELECT b.id, b.title FROM book_author ba\n" +
                "  LEFT JOIN book b on ba.book_id = b.id\n" +
                "  WHERE author_id = ?";
        TypedQuery<DbBook> query = entityManager.createQuery("SELECT b FROM DbBook b WHERE b", DbBook.class);
        return query.getResultList();
    }
}
