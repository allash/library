package ru.otus.library.domain.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.entities.DbGenre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GenreRepositoryJdbc implements GenreRepository {

    private final NamedParameterJdbcOperations namedParamsJdbcOperations;

    public GenreRepositoryJdbc(NamedParameterJdbcOperations namedParamsJdbcOperations) {
        this.namedParamsJdbcOperations = namedParamsJdbcOperations;
    }

    @Override
    public int count() {
        return namedParamsJdbcOperations.getJdbcOperations().queryForObject("SELECT count(*) FROM genre", Integer.class);
    }

    @Override
    public DbGenre findById(Integer id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return namedParamsJdbcOperations.queryForObject("SELECT * FROM genre where id = :id", params, new GenreRowMapper());
    }

    @Override
    public List<DbGenre> findAll() {
        return namedParamsJdbcOperations.query("SELECT * FROM genre", new GenreRowMapper());
    }
    @Override
    public DbGenre save(DbGenre entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO genre (name) VALUES (?)";

        namedParamsJdbcOperations.getJdbcOperations().update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, entity.getName());
            return preparedStatement;
        }, keyHolder);

        return findById((Integer) keyHolder.getKey());
    }

    private static class GenreRowMapper implements RowMapper<DbGenre> {
        @Override
        public DbGenre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Integer id = rs.getInt("id");
            String name = rs.getString("name");
            return new DbGenre(id, name);
        }
    }
}
