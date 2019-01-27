package ru.otus.library.app.book.dto.request;

import javax.validation.constraints.NotEmpty;

public class DtoCreateCommentRequest {

    @NotEmpty
    private String text;

    public DtoCreateCommentRequest() {
    }

    public DtoCreateCommentRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
