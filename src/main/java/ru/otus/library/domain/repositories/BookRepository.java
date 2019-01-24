package ru.otus.library.domain.repositories;

import ru.otus.library.domain.entities.DbBook;

import java.util.Collection;
import java.util.List;

public interface BookRepository {
    int count();
    DbBook findById(Integer id);
    List<DbBook> findAll();
    DbBook save(DbBook entity);
    List<DbBook> findByIdIn(Collection<Integer> collection);
    List<DbBook> findByAuthorId(Integer authorId);
}
