package ru.otus.library.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.entities.DbGenre;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;

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

            DbBook book1 = fixtures.createBook("Book 1", Arrays.asList(genre1, genre2));
            DbBook book2 = fixtures.createBook("Book 2", Collections.singletonList(genre1));
            DbBook book3 = fixtures.createBook("Book 3", Collections.singletonList(genre2));

            fixtures.createAuthor("John", "Doe", Arrays.asList(book1, book2));
            fixtures.createAuthor("Mark", "Foo", Collections.singletonList(book3));
            fixtures.createAuthor("Bill", "NoBooks");
        }
    }
}
