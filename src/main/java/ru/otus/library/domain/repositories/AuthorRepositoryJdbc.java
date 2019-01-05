package ru.otus.library.domain.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.entities.DbAuthor;
import ru.otus.library.domain.entities.DbBook;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        createOrUpdate(keyHolder, author);
        author.setId((Integer) keyHolder.getKey());

        updateBooks(author);

        return author;
    }

    private String getCreateOrUpdateSql(DbAuthor author) {
        return author.getId() != null
                ? "UPDATE author SET (first_name, last_name) = (?, ?) WHERE id = ?"
                : "INSERT INTO author (first_name, last_name) VALUES (?, ?)";
    }

    private void createOrUpdate(KeyHolder keyHolder, DbAuthor author) {
        namedParamsJdbcOperations.getJdbcOperations().update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(getCreateOrUpdateSql(author), new String[]{"id"});
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            if (author.getId() != null) {
                preparedStatement.setInt(3, author.getId());
            }
            return preparedStatement;
        }, keyHolder);
    }

    private void updateBooks(DbAuthor author) {
        if (author.getBooks() == null || author.getBooks().size() == 0) return;

        String bookIds = author.getBooks()
                .stream()
                .map(it -> "(" + it.getId() + ")")
                .collect(Collectors.joining(","));

        String sql = "WITH\n" +
                "    b (id) AS (\n" +
                "      SELECT *\n" +
                "      FROM book_author\n" +
                "      WHERE author_id = ? \n" +
                "  ),\n" +
                "    v (id) AS (\n" +
                "    VALUES " + bookIds + " \n" +
                "  ),\n" +
                "    sel (id) AS (\n" +
                "      SELECT v.id\n" +
                "      FROM v\n" +
                "        LEFT JOIN b ON b.id = v.id\n" +
                "      WHERE b.id IS NULL\n" +
                "  )\n" +
                "INSERT INTO book_author (book_id, author_id)\n" +
                "  SELECT\n" +
                "    sel.id,\n" +
                "    ? \n" +
                "  FROM sel;";

        if (author.getId() != null) {
            String prefixUpdate = "BEGIN;\n" +
                    "DELETE FROM book_author\n" +
                    "WHERE author_id = ?; \n";

            String updateSql = prefixUpdate + sql + "COMMIT;";

            namedParamsJdbcOperations.getJdbcOperations().update(updateSql, author.getId(), author.getId(), author.getId());
        } else {
            namedParamsJdbcOperations.getJdbcOperations().update(sql, author.getId(), author.getId());
        }
    }

    @Override
    public DbAuthor findById(Integer id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return namedParamsJdbcOperations.queryForObject("SELECT * FROM author where id = :id", params, new AuthorRowMapper());
    }

    @Override
    public List<DbAuthor> findAll() {

        String query = "SELECT\n" +
                "  a.id AS author_id,\n" +
                "  a.first_name AS author_first_name,\n" +
                "  a.last_name AS author_last_name,\n" +
                "  b.id AS book_id,\n" +
                "  b.title AS book_title\n" +
                "FROM author a\n" +
                "  LEFT JOIN book_author ba on a.id = ba.author_id\n" +
                "  LEFT JOIN book b on ba.book_id = b.id";

        List<AuthorBook> result = namedParamsJdbcOperations.query(query, new AuthorBookMapper());

        Map<Integer, DbAuthor> authorMap = new HashMap<>();
        for (AuthorBook authorBook : result) {
            DbAuthor author = authorMap.getOrDefault(authorBook.authorId, new DbAuthor(authorBook.authorId, authorBook.authorFirstName, authorBook.authorLastName));
            if (authorBook.bookId > 0) {
                author.getBooks().add(new DbBook(authorBook.bookId, authorBook.bookTitle));
            }
            authorMap.put(authorBook.authorId, author);
        }

        return new ArrayList<>(authorMap.values());
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

    private static class AuthorBookMapper implements RowMapper<AuthorBook> {
        @Override
        public AuthorBook mapRow(ResultSet rs, int rowNum) throws SQLException {

            Integer authorId = rs.getInt("author_id");
            String authorFirstName = rs.getString("author_first_name");
            String authorLastName = rs.getString("author_last_name");
            Integer bookId = rs.getInt("book_id");
            String bookTitle = rs.getString("book_title");

            return new AuthorBook(authorId, authorFirstName, authorLastName, bookId, bookTitle);
        }
    }

    private static class AuthorBook {
        Integer authorId;
        String authorFirstName;
        String authorLastName;
        Integer bookId;
        String bookTitle;

        AuthorBook(Integer authorId, String authorFirstName, String authorLastName, Integer bookId, String bookTitle) {
            this.authorId = authorId;
            this.authorFirstName = authorFirstName;
            this.authorLastName = authorLastName;
            this.bookId = bookId;
            this.bookTitle = bookTitle;
        }
    }
}
