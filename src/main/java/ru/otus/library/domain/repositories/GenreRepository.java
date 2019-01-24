package ru.otus.library.domain.repositories;

import ru.otus.library.domain.entities.DbGenre;

import java.util.Collection;
import java.util.List;

public interface GenreRepository {
    int count();
    DbGenre findById(Integer id);
    List<DbGenre> findAll();
    DbGenre save(DbGenre entity);
    List<DbGenre> findByIdIn(Collection<Long> collection);
}