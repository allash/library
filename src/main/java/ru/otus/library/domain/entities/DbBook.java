package ru.otus.library.domain.entities;

public class DbBook {

    private int id;
    private String title;

    public DbBook() { }

    public DbBook(String title) {
        this.title = title;
    }

    public DbBook(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
