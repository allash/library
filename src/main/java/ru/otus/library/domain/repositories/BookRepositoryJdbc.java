package ru.otus.library.domain.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.entities.DbAuthor;
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

        String query = "SELECT\n" +
                "  b.id AS book_id,\n" +
                "  b.title AS book_title,\n" +
                "  g.id AS genre_id,\n" +
                "  g.name AS genre_name,\n" +
                "  a.id AS author_id,\n" +
                "  a.first_name AS author_first_name,\n" +
                "  a.last_name AS author_last_name\n" +
                "FROM book b\n" +
                "          LEFT JOIN book_genre bg on b.id = bg.book_id\n" +
                "          LEFT JOIN genre g on bg.genre_id = g.id\n" +
                "          LEFT JOIN book_author ba on b.id = ba.book_id\n" +
                "          LEFT JOIN author a on ba.author_id = a.id";

        Map<Integer, BookResultSet> bookResultSetMap = new HashMap<>();

        namedParamsJdbcOperations.query(query, rs -> {
            Integer bookId = rs.getInt("book_id");

            BookResultSet bookResultSet = bookResultSetMap.getOrDefault(bookId, new BookResultSet());

            Integer authorId = rs.getInt("author_id");
            if (authorId > 0) {
                String firstName = rs.getString("author_first_name");
                String lastName = rs.getString("author_last_name");
                bookResultSet.authors.add(new DbAuthor(authorId, firstName, lastName));
            }

            Integer genreId = rs.getInt("genre_id");
            if (genreId > 0) {
                String name = rs.getString("genre_name");
                bookResultSet.genres.add(new DbGenre(genreId, name));
            }

            bookResultSet.bookTitle = rs.getString("book_title");

            bookResultSetMap.put(bookId, bookResultSet);
        });

        return bookResultSetMap.entrySet()
                .stream()
                .map(it ->
                    new DbBook(it.getKey(), it.getValue().bookTitle,
                            new ArrayList<DbGenre>(it.getValue().genres),
                            new ArrayList<DbAuthor>(it.getValue().authors))
                )
                .collect(Collectors.toList());

    }

    @Override
    public List<DbBook> findByAuthorId(Integer authorId) {
        String sql = "SELECT b.id, b.title FROM book_author ba\n" +
                "  LEFT JOIN book b on ba.book_id = b.id\n" +
                "  WHERE author_id = ?";
        return namedParamsJdbcOperations.getJdbcOperations().query(sql, new BookRowMapper(), authorId);
    }

    private class BookResultSet {
        String bookTitle;
        Set<DbAuthor> authors = new HashSet();
        Set<DbGenre> genres = new HashSet();
    }

    @Override
    public DbBook save(DbBook book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        createOrUpdate(keyHolder, book);
        book.setId((Integer) keyHolder.getKey());

        batchUpdateGenres(book.getId(), book.getGenres());
        batchUpdateAuthors(book.getId(), book.getAuthors());

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

    private void cleanBookGenresByBookId(Integer bookId) {
        namedParamsJdbcOperations.getJdbcOperations().update("DELETE FROM book_genre WHERE book_id = ?", bookId);
    }

    private void cleanBookAuthorsByBookId(Integer bookId) {
        namedParamsJdbcOperations.getJdbcOperations().update("DELETE FROM book_author WHERE book_id = ?", bookId);
    }

    private void batchUpdateGenres(Integer bookId, final List<DbGenre> genres) {

        cleanBookGenresByBookId(bookId);

        namedParamsJdbcOperations.getJdbcOperations().batchUpdate( // step 3
                "insert into book_genre (book_id, genre_id) values (?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, bookId);
                        ps.setInt(2, genres.get(i).getId());
                    }

                    public int getBatchSize() {
                        return genres.size();
                    }
                } );
    }

    private void batchUpdateAuthors(Integer bookId, final List<DbAuthor> authors) {

        cleanBookAuthorsByBookId(bookId);

        namedParamsJdbcOperations.getJdbcOperations().batchUpdate(
                "INSERT INTO book_author(book_id, author_id) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, bookId);
                        ps.setInt(2, authors.get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return authors.size();
                    }
                }
        );
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
