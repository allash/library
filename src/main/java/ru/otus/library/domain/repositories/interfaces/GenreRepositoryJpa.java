package ru.otus.library.domain.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.entities.DbGenre;

import java.util.List;

@Repository
public interface GenreRepositoryJpa extends
        JpaRepository<DbGenre, Long>,
        JpaSpecificationExecutor<DbGenre> {
    List<DbGenre> findByIdIn(List<Long> ids);
}
