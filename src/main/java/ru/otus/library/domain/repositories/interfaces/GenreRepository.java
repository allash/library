package ru.otus.library.domain.repositories.interfaces;

import ru.otus.library.domain.entities.DbGenre;

import java.util.Collection;
import java.util.List;

public interface GenreRepository {
    long count();
    DbGenre findById(Long id);
    List<DbGenre> findAll();
    DbGenre save(DbGenre entity);
    List<DbGenre> findByIdIn(Collection<Long> collection);
}
