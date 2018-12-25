package ru.otus.library.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.entities.DbAuthor;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.entities.DbGenre;
import ru.otus.library.domain.repositories.AuthorRepository;
import ru.otus.library.domain.repositories.BookRepository;
import ru.otus.library.domain.repositories.GenreRepository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@AutoConfigureBefore({ FlywayAutoConfiguration.class })
public class DatabaseConfig {

    @Bean
    public Flyway flyway(DataSource dataSource)  {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.clean();
        flyway.migrate();
        return flyway;
    }

    @Bean
    public FlywayMigrationInitializer flywayInitializer(Flyway flyway) {
        return new FlywayMigrationInitializer(flyway, null);
    }

    @Component
    public class FixtureLoader {

        private AuthorRepository authorRepository;
        private GenreRepository genreRepository;
        private BookRepository bookRepository;

        public FixtureLoader(AuthorRepository authorRepository, GenreRepository genreRepository, BookRepository bookRepository) {
            this.authorRepository = authorRepository;
            this.genreRepository = genreRepository;
            this.bookRepository = bookRepository;
        }

        @Transactional
        @PostConstruct
        public void load() {

            DbBook book1 = bookRepository.save(new DbBook("Alien"));
            System.out.println(book1.getId() + " " + book1.getTitle());

            DbAuthor author = authorRepository.save(new DbAuthor("John", "Doe"));
            DbAuthor author2 = authorRepository.save(new DbAuthor("Jack", "Foo"));

            System.out.println(author.getId() + " - " + author.getFirstName());
            System.out.println(author2.getId() + " - " + author2.getFirstName());

            DbGenre genre1 = genreRepository.save(new DbGenre("Fantasy"));

            System.out.println(genre1.getId() + " " + genre1.getName());
        }
    }
}
