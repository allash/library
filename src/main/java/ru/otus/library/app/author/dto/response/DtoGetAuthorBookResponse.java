package ru.otus.library.app.author.dto.response;

public class DtoGetAuthorBookResponse {

    private int id;
    private String title;

    public DtoGetAuthorBookResponse() {
    }

    public DtoGetAuthorBookResponse(int id, String title) {
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
