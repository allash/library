package ru.otus.library.domain.repositories.interfaces;

import ru.otus.library.domain.entities.DbAuthor;

import java.util.Collection;
import java.util.List;

public interface AuthorRepository {
    long count();
    DbAuthor findById(Long id);
    List<DbAuthor> findAll();
    DbAuthor save(DbAuthor entity);
    List<DbAuthor> findByIdIn(Collection<Long> collection);
}
