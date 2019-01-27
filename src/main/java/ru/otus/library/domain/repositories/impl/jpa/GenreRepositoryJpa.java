package ru.otus.library.domain.repositories.impl.jpa;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.entities.DbGenre;
import ru.otus.library.domain.repositories.interfaces.GenreRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Primary
@Repository
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long count() {
        return (long) entityManager.createQuery("SELECT COUNT(g) FROM DbGenre g").getSingleResult();
    }

    @Override
    public DbGenre findById(Long id) {
        return entityManager.find(DbGenre.class, id);
    }

    @Override
    public List<DbGenre> findAll() {
        TypedQuery<DbGenre> query = entityManager.createQuery("SELECT g FROM DbGenre g", DbGenre.class);
        return query.getResultList();
    }

    @Override
    public DbGenre save(DbGenre entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<DbGenre> findByIdIn(Collection<Long> collection) {
        TypedQuery<DbGenre> query = entityManager.createQuery("SELECT g FROM DbGenre g WHERE g.id IN (:ids)", DbGenre.class);
        query.setParameter("ids", collection);
        return query.getResultList();
    }
}
