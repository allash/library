package ru.otus.library.app.book.dto.response;

public class DtoGetBookCommentResponse {

    private Long id;
    private String text;

    public DtoGetBookCommentResponse() {
    }

    public DtoGetBookCommentResponse(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
