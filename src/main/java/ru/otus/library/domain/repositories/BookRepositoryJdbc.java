package ru.otus.library.domain.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.entities.DbBook;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepositoryJdbc implements BookRepository {

    private final NamedParameterJdbcOperations namedParamsJdbcOperations;

    @Autowired
    public BookRepositoryJdbc(NamedParameterJdbcOperations namedParamsJdbcOperations) {
        this.namedParamsJdbcOperations = namedParamsJdbcOperations;
    }

    @Override
    public int count() {
        return namedParamsJdbcOperations.getJdbcOperations().queryForObject("SELECT count(*) FROM book", Integer.class);
    }

    @Override
    public DbBook findById(Integer id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return namedParamsJdbcOperations.queryForObject("SELECT * FROM book where id = :id", params, new BookRowMapper());
    }

    @Override
    public List<DbBook> findAll() {
        return namedParamsJdbcOperations.query("SELECT * FROM book", new BookRowMapper());
    }

    @Override
    public DbBook save(DbBook entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO book (title) VALUES (?)";

        namedParamsJdbcOperations.getJdbcOperations().update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{ "id" });
            preparedStatement.setString(1, entity.getTitle());
            return preparedStatement;
        }, keyHolder);

        return findById((Integer) keyHolder.getKey());
    }

    private static class BookRowMapper implements RowMapper<DbBook> {
        @Override
        public DbBook mapRow(ResultSet rs, int rowNum) throws SQLException {
            Integer id = rs.getInt("id");
            String title = rs.getString("title");
            return new DbBook(id, title);
        }
    }
}
