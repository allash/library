package ru.otus.library.app.book.dto.response;

import java.util.List;

public class DtoGetBookResponse {

    private Integer id;

    private String title;

    private List<DtoGetBookGenre> genres;

    private List<DtoGetBookAuthor> authors;

    public DtoGetBookResponse() {
    }

    public DtoGetBookResponse(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public DtoGetBookResponse(Integer id, String title, List<DtoGetBookGenre> genres, List<DtoGetBookAuthor> authors) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.authors = authors;
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

    public List<DtoGetBookGenre> getGenres() {
        return genres;
    }

    public void setGenres(List<DtoGetBookGenre> genres) {
        this.genres = genres;
    }

    public List<DtoGetBookAuthor> getAuthors() {
        return authors;
    }

    public void setAuthors(List<DtoGetBookAuthor> authors) {
        this.authors = authors;
    }

    public static class DtoGetBookGenre {
        private Integer id;

        private String name;

        public DtoGetBookGenre() {
        }

        public DtoGetBookGenre(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class DtoGetBookAuthor {
        private Integer id;
        private String firstName;
        private String lastName;

        public DtoGetBookAuthor() {
        }

        public DtoGetBookAuthor(Integer id, String firstName, String lastName) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
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
}
