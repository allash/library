package ru.otus.library.domain.entities;

import java.util.ArrayList;
import java.util.List;

public class DbAuthor {

    private Integer id;
    private String firstName;
    private String lastName;

    private List<DbBook> books;

    public DbAuthor() { }

    public DbAuthor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = new ArrayList<>();
    }

    public DbAuthor(Integer id, String firstName, String lastName) {
        this (firstName, lastName);
        this.id = id;
    }

    public DbAuthor(String firstName, String lastName, List<DbBook> books) {
        this (firstName, lastName);
        this.books = books;
    }

    public DbAuthor(Integer id, String firstName, String lastName, List<DbBook> books) {
        this (id, firstName, lastName);
        this.books = books;
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
