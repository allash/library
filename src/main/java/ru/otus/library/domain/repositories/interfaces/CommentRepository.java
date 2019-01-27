package ru.otus.library.domain.repositories.interfaces;

import ru.otus.library.domain.entities.DbComment;

import java.util.List;

public interface CommentRepository {
    List<DbComment> findAllByBookId(Long bookId);
    DbComment save(DbComment comment);
}
