package ru.otus.library.domain.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
public class DbBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "book_genre",
            joinColumns = { @JoinColumn(name = "book_id") },
            inverseJoinColumns = { @JoinColumn(name = "genre_id") }
    )
    private List<DbGenre> genres;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "book_author",
            joinColumns = { @JoinColumn(name = "book_id") },
            inverseJoinColumns = { @JoinColumn(name = "author_id") }
    )
    private List<DbAuthor> authors;

    public DbBook() { }

    public DbBook(String title) {
        this.title = title;
        this.genres = new ArrayList<>();
        this.authors = new ArrayList<>();
    }

    public DbBook(Long id, String title) {
        this (title);
        this.id = id;
    }

    public DbBook(String title, List<DbGenre> genres, List<DbAuthor> authors) {
        this (title);
        this.genres = genres;
        this.authors = authors;
    }

    public DbBook(Long id, String title, List<DbGenre> genres, List<DbAuthor> authors) {
        this (id, title);
        this.genres = genres;
        this.authors = authors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DbGenre> getGenres() {
        return genres;
    }

    public void setGenres(List<DbGenre> genres) {
        this.genres = genres;
    }

    public List<DbAuthor> getAuthors() {
        return authors;
    }

    public void setAuthors(List<DbAuthor> authors) {
        this.authors = authors;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DbBook other = (DbBook) obj;
        if (id == null)
            return other.getId() == null;
        else
            return id.equals(other.id);
    }
}
