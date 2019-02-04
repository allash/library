package ru.otus.library.domain.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.entities.DbBook;

import java.util.List;

@Repository
public interface BookRepository extends
        JpaRepository<DbBook, Long>,
        JpaSpecificationExecutor<DbBook> {
    List<DbBook> findByAuthorsId(Long id);
}
