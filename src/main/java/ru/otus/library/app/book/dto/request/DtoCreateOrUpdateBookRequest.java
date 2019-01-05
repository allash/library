package ru.otus.library.app.book.dto.request;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class DtoCreateOrUpdateBookRequest {

    @NotBlank
    private String title;

    private List<Integer> genreIds;

    public DtoCreateOrUpdateBookRequest() {
    }

    public DtoCreateOrUpdateBookRequest(@NotBlank String title) {
        this.title = title;
    }

    public DtoCreateOrUpdateBookRequest(@NotBlank String title, List<Integer> genreIds) {
        this.title = title;
        this.genreIds = genreIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }
}
