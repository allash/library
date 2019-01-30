package ru.otus.library.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.entities.DbAuthor;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.entities.DbGenre;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;

@Configuration
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

        private FixtureGenerator fixtures;

        public FixtureLoader(
                FixtureGenerator fixtures) {
            this.fixtures = fixtures;
        }

        @Transactional
        @PostConstruct
        public void load() {
            DbGenre genre1 = fixtures.createGenre("Fantasy");
            DbGenre genre2 = fixtures.createGenre("Novel");

            DbAuthor author1 = fixtures.createAuthor("John", "Doe");
            DbAuthor author2 = fixtures.createAuthor("Mark", "Foo");
            DbAuthor author3 = fixtures.createAuthor("Bill", "NoBooks");

            DbBook book1 = fixtures.createBook("Book 1", Arrays.asList(genre1, genre2), Arrays.asList(author1, author2));
            DbBook book2 = fixtures.createBook("Book 2", Collections.singletonList(genre1), Arrays.asList(author1, author3));
            fixtures.createBook("Book 3", Collections.singletonList(genre2), Arrays.asList(author2, author3));

            // Generate comments

            fixtures.createComment(book1, "Some random text 1");
            fixtures.createComment(book1, "Some random text 2");

            fixtures.createComment(book2, "Some random text 1");
            fixtures.createComment(book2, "Some random text 2");
            fixtures.createComment(book2, "Some random text 3");
        }
    }
}
