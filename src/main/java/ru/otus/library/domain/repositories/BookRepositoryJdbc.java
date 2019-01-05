package ru.otus.library.domain.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.entities.DbBook;
import ru.otus.library.domain.entities.DbGenre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

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
    public List<DbBook> findByIdIn(Collection<Integer> collection) {

        Map namedParams = Collections.singletonMap("ids", collection);
        return namedParamsJdbcOperations.query("SELECT * FROM book WHERE id IN (:ids)", namedParams, new BookRowMapper());
    }

    @Override
    public List<DbBook> findAll() {

        String query = "SELECT b.id AS book_id, b.title AS book_title, g.id AS genre_id, g.name AS genre_name FROM book b\n" +
                "  LEFT JOIN book_genre bg on b.id = bg.book_id\n" +
                "  LEFT JOIN genre g on bg.genre_id = g.id";

        List<BookGenre> result = namedParamsJdbcOperations.query(query, new BookGenreRowMapper());

        Map<Integer, DbBook> books = new HashMap<>();
        for (BookGenre bookGenre : result) {
            DbBook book = books.getOrDefault(bookGenre.bookId, new DbBook(bookGenre.bookId, bookGenre.bookTitle));
            if (bookGenre.genreId > 0) {
                book.getGenres().add(new DbGenre(bookGenre.genreId, bookGenre.genreName));
            }
            books.put(bookGenre.bookId, book);
        }

        return new ArrayList<>(books.values());
    }

    @Override
    public DbBook save(DbBook book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        createOrUpdate(keyHolder, book);
        book.setId((Integer) keyHolder.getKey());

        updateGenres(book);

        return book;
    }

    private String getCreateOrUpdateSql(DbBook book) {
        return book.getId() != null
                ? "UPDATE book SET title = ? WHERE id = ?"
                : "INSERT INTO book (title) VALUES (?)";
    }

    private void createOrUpdate(KeyHolder keyHolder, DbBook book) {
        namedParamsJdbcOperations.getJdbcOperations().update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(getCreateOrUpdateSql(book), new String[]{ "id" });
            preparedStatement.setString(1, book.getTitle());
            if (book.getId() != null) { preparedStatement.setInt(2, book.getId()); }
            return preparedStatement;
        }, keyHolder);
    }

    private void updateGenres(DbBook book) {
        if (book.getGenres() == null || book.getGenres().size() == 0) return;

        String genreIds = book.getGenres()
                .stream()
                .map(it -> "(" + it.getId() + ")")
                .collect(Collectors.joining(","));

        String sql = "WITH\n" +
                "    b (id) AS (\n" +
                "      SELECT *\n" +
                "      FROM book_genre\n" +
                "      WHERE book_id = ?\n" +
                "  ),\n" +
                "    v (id) AS (\n" +
                "    VALUES " + genreIds + "\n" +
                "  ),\n" +
                "    sel (id) AS (\n" +
                "      SELECT v.id\n" +
                "      FROM v\n" +
                "        LEFT JOIN b ON b.id = v.id\n" +
                "      WHERE b.id IS NULL\n" +
                "  )\n" +
                "INSERT INTO book_genre (genre_id, book_id)\n" +
                "  SELECT\n" +
                "    sel.id,\n" +
                "    ?\n" +
                "  FROM sel;";

        if (book.getId() != null) {
            String prefixUpdate = "BEGIN;\n" +
                    "DELETE FROM book_genre\n" +
                    "WHERE book_id = ?; \n";

            String updateSql = prefixUpdate + sql + "COMMIT;";

            namedParamsJdbcOperations.getJdbcOperations().update(updateSql, book.getId(), book.getId(), book.getId());
        } else {
            namedParamsJdbcOperations.getJdbcOperations().update(sql, book.getId(), book.getId());
        }
    }

    private static class BookGenre {
        Integer bookId;
        String bookTitle;
        Integer genreId;
        String genreName;

        BookGenre(Integer bookId, String bookTitle, Integer genreId, String genreName) {
            this.bookId = bookId;
            this.bookTitle = bookTitle;
            this.genreId = genreId;
            this.genreName = genreName;
        }
    }

    private static class BookGenreRowMapper implements RowMapper<BookGenre> {
        @Override
        public BookGenre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Integer bookId = rs.getInt("book_id");
            String bookTitle = rs.getString("book_title");
            Integer genreId = rs.getInt("genre_id");
            String genreName = rs.getString("genre_name");

            return new BookGenre(bookId, bookTitle, genreId, genreName);
        }
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
