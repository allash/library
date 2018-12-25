package ru.otus.library.domain.entities;

import java.util.List;

public class DbAuthor {

    private Integer id;
    private String firstName;
    private String lastName;

    private List<DbBook> books;

    public DbAuthor() { }

    public DbAuthor(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public DbAuthor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public List<DbBook> getBooks() {
        return books;
    }

    public void setBooks(List<DbBook> books) {
        this.books = books;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
