package ru.otus.library.domain.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.entities.DbAuthor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorRepositoryJdbc implements AuthorRepository {

    private final NamedParameterJdbcOperations namedParamsJdbcOperations;

    public AuthorRepositoryJdbc(NamedParameterJdbcOperations namedParamsJdbcOperations) {
        this.namedParamsJdbcOperations = namedParamsJdbcOperations;
    }

    @Override
    public int count() {
        return namedParamsJdbcOperations.getJdbcOperations().queryForObject("SELECT count(*) FROM author", Integer.class);
    }

    @Override
    public DbAuthor save(DbAuthor author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO author (first_name, last_name) VALUES (?, ?)";

        namedParamsJdbcOperations.getJdbcOperations().update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            return preparedStatement;
        }, keyHolder);

        return findById((Integer) keyHolder.getKey());
    }

    @Override
    public DbAuthor findById(Integer id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return namedParamsJdbcOperations.queryForObject("SELECT * FROM author where id = :id", params, new AuthorRowMapper());
    }

    @Override
    public List<DbAuthor> findAll() {
        return namedParamsJdbcOperations.query("SELECT * FROM author", new AuthorRowMapper());
    }

    private static class AuthorRowMapper implements RowMapper<DbAuthor> {
        @Override
        public DbAuthor mapRow(ResultSet rs, int rowNum) throws SQLException {
            Integer id = rs.getInt("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            return new DbAuthor(id, firstName, lastName);
        }
    }
}
