package ru.otus.library.domain.entities;

public class DbGenre {

    private int id;
    private String name;

    public DbGenre() { }

    public DbGenre(String name) {
        this.name = name;
    }

    public DbGenre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
