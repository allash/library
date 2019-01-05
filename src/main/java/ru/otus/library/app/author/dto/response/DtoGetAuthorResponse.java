package ru.otus.library.app.author.dto.response;

import java.util.List;

public class DtoGetAuthorResponse {

    private Integer id;

    private String firstName;

    private String lastName;

    private List<AuthorBook> books;

    public DtoGetAuthorResponse() {
    }

    public DtoGetAuthorResponse(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public DtoGetAuthorResponse(Integer id, String firstName, String lastName, List<AuthorBook> books) {
        this (id, firstName, lastName);
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

    public List<AuthorBook> getBooks() {
        return books;
    }

    public void setBooks(List<AuthorBook> books) {
        this.books = books;
    }

    public static class AuthorBook {
        private Integer id;
        private String title;

        public AuthorBook() {
        }

        public AuthorBook(Integer id, String title) {
            this.id = id;
            this.title = title;
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
    }
}