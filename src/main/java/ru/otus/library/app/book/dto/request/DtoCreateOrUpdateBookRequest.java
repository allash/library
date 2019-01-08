package ru.otus.library.app.book.dto.request;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class DtoCreateOrUpdateBookRequest {

    @NotBlank
    private String title;

    private List<Long> genreIds;

    private List<Long> authorIds;

    public DtoCreateOrUpdateBookRequest() {
    }

    public DtoCreateOrUpdateBookRequest(@NotBlank String title) {
        this.title = title;
    }

    public DtoCreateOrUpdateBookRequest(@NotBlank String title, List<Long> genreIds, List<Long> authorIds) {
        this.title = title;
        this.genreIds = genreIds;
        this.authorIds = authorIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Long> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Long> genreIds) {
        this.genreIds = genreIds;
    }

    public List<Long> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<Long> authorIds) {
        this.authorIds = authorIds;
    }
}
