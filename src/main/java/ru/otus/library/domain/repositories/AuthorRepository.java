package ru.otus.library.domain.repositories;

import ru.otus.library.domain.entities.DbAuthor;

import java.util.Collection;
import java.util.List;

public interface AuthorRepository {
    int count();
    DbAuthor findById(Integer id);
    List<DbAuthor> findAll();
    DbAuthor save(DbAuthor entity);
    List<DbAuthor> findByIdIn(Collection<Integer> collection);
}
