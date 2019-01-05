package ru.otus.library.domain.entities;

import java.util.ArrayList;
import java.util.List;

public class DbBook {

    private Integer id;
    private String title;

    private List<DbGenre> genres;

    public DbBook() { }

    public DbBook(String title) {
        this.title = title;
        this.genres = new ArrayList<>();
    }

    public DbBook(int id, String title) {
        this (title);
        this.id = id;
    }

    public DbBook(String title, List<DbGenre> genres) {
        this (title);
        this.genres = genres;
    }

    public DbBook(Integer id, String title, List<DbGenre> genres) {
        this (id, title);
        this.genres = genres;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}
