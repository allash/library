package ru.otus.library.domain.repositories;

import ru.otus.library.domain.entities.DbBook;

import java.util.List;

public interface BookRepository {
    int count();
    DbBook findById(Integer id);
    List<DbBook> findAll();
    DbBook save(DbBook entity);
}
