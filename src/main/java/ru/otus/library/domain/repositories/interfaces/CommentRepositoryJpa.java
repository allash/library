package ru.otus.library.domain.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.entities.DbComment;

import java.util.List;

@Repository
public interface CommentRepositoryJpa extends
        JpaRepository<DbComment, Long>,
        JpaSpecificationExecutor<DbComment> {
    List<DbComment> findByBookId(Long id);
}
