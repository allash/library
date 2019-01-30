package ru.otus.library.domain.repositories.interfaces;

import ru.otus.library.domain.entities.DbBook;

import java.util.Collection;
import java.util.List;

public interface BookRepository {
    long count();
    DbBook findById(Long id);
    List<DbBook> findAll();
    DbBook save(DbBook entity);
    List<DbBook> findByIdIn(Collection<Long> collection);
    List<DbBook> findByAuthorId(Long authorId);
}
