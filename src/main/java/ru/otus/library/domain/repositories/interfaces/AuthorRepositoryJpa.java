package ru.otus.library.domain.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.entities.DbAuthor;

import java.util.List;

@Repository
public interface AuthorRepositoryJpa extends
        JpaRepository<DbAuthor, Long>,
        JpaSpecificationExecutor<DbAuthor> {
    List<DbAuthor> findByIdIn(List<Long> ids);
}
