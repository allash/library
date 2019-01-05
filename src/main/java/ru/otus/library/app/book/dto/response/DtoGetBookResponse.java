package ru.otus.library.app.book.dto.response;

import java.util.List;

public class DtoGetBookResponse {

    private Integer id;

    private String title;

    private List<DtoGetBookGenre> genres;

    public DtoGetBookResponse() {
    }

    public DtoGetBookResponse(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public DtoGetBookResponse(Integer id, String title, List<DtoGetBookGenre> genres) {
        this.id = id;
        this.title = title;
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

    public List<DtoGetBookGenre> getGenres() {
        return genres;
    }

    public void setGenres(List<DtoGetBookGenre> genres) {
        this.genres = genres;
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
}
