package ru.otus.library.app.author.dto.response;

public class DtoGetAuthorBookResponse {

    private Long id;
    private String title;

    public DtoGetAuthorBookResponse() {
    }

    public DtoGetAuthorBookResponse(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
