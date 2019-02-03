package ru.otus.library.domain.repositories.impl.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.entities.DbGenre;
import ru.otus.library.domain.repositories.interfaces.old.GenreRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class GenreRepositoryJdbcImpl implements GenreRepository {

    private final NamedParameterJdbcOperations namedParamsJdbcOperations;

    public GenreRepositoryJdbcImpl(NamedParameterJdbcOperations namedParamsJdbcOperations) {
        this.namedParamsJdbcOperations = namedParamsJdbcOperations;
    }

    public long count() {
        return namedParamsJdbcOperations.getJdbcOperations().queryForObject("SELECT count(*) FROM genre", Long.class);
    }

    @Override
    public DbGenre findById(Long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return namedParamsJdbcOperations.queryForObject("SELECT * FROM genre where id = :id", params, new GenreRowMapper());
    }

    @Override
    public List<DbGenre> findByIdIn(Collection<Long> collection) {
        Map namedParams = Collections.singletonMap("ids", collection);
        return namedParamsJdbcOperations.query("SELECT * FROM genre WHERE id IN (:ids)", namedParams, new GenreRowMapper());
    }

    @Override
    public List<DbGenre> findAll() {
        return namedParamsJdbcOperations.query("SELECT * FROM genre", new GenreRowMapper());
    }

    @Override
    public DbGenre save(DbGenre entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        createOrUpdate(keyHolder, entity);
        entity.setId(keyHolder.getKey().longValue());

        return entity;
    }

    private String getCreateOrUpdateSql(DbGenre genre) {
        return genre.getId() != null
                ? "UPDATE genre SET name = ? WHERE id = ?"
                : "INSERT INTO genre (name) VALUES (?)";
    }

    private void createOrUpdate(KeyHolder keyHolder, DbGenre genre) {
        namedParamsJdbcOperations.getJdbcOperations().update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(getCreateOrUpdateSql(genre), new String[]{ "id" });
            preparedStatement.setString(1, genre.getName());
            if (genre.getId() != null) { preparedStatement.setLong(2, genre.getId()); }
            return preparedStatement;
        }, keyHolder);
    }

    private static class GenreRowMapper implements RowMapper<DbGenre> {
        @Override
        public DbGenre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            return new DbGenre(id, name);
        }
    }
}
