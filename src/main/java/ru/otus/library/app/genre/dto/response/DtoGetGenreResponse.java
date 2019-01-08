package ru.otus.library.app.genre.dto.response;

public class DtoGetGenreResponse {

    private Long id;

    private String name;

    public DtoGetGenreResponse() {
    }

    public DtoGetGenreResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
