package ru.otus.library.domain.repositories.impl.jpa;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.entities.DbAuthor;
import ru.otus.library.domain.repositories.interfaces.AuthorRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Primary
@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long count() {
        return (long) entityManager.createQuery("SELECT COUNT(a) FROM DbAuthor a").getSingleResult();
    }

    @Override
    public DbAuthor findById(Long id) {
        return entityManager.find(DbAuthor.class, id);
    }

    @Override
    public List<DbAuthor> findAll() {
        TypedQuery<DbAuthor> query = entityManager.createQuery("SELECT a FROM DbAuthor a", DbAuthor.class);
        return query.getResultList();
    }

    @Override
    public DbAuthor save(DbAuthor entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<DbAuthor> findByIdIn(Collection<Long> collection) {
        TypedQuery<DbAuthor> query = entityManager.createQuery("SELECT a FROM DbAuthor a WHERE a.id IN (:ids)", DbAuthor.class);
        query.setParameter("ids", collection);
        return query.getResultList();
    }
}
