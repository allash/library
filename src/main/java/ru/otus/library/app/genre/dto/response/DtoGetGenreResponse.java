package ru.otus.library.app.genre.dto.response;

public class DtoGetGenreResponse {

    private Integer id;

    private String name;

    public DtoGetGenreResponse() {
    }

    public DtoGetGenreResponse(Integer id, String name) {
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
