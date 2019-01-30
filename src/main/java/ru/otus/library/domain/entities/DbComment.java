package ru.otus.library.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class DbComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name="book_id", nullable = false)
    private DbBook book;

    @Column(name = "book_id", updatable = false, insertable = false)
    private Long bookId;

    public DbComment() {
    }

    public DbComment(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DbBook getBook() {
        return book;
    }

    public void setBook(DbBook book) {
        this.book = book;
        this.bookId = book.getId();
    }

    public Long getBookId() {
        return bookId;
    }
}
