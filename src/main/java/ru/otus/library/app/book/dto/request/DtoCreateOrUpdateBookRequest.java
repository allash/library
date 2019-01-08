package ru.otus.library.app.book.dto.request;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class DtoCreateOrUpdateBookRequest {

    @NotBlank
    private String title;

    private List<Integer> genreIds;

    private List<Integer> authorIds;

    public DtoCreateOrUpdateBookRequest() {
    }

    public DtoCreateOrUpdateBookRequest(@NotBlank String title) {
        this.title = title;
    }

    public DtoCreateOrUpdateBookRequest(@NotBlank String title, List<Integer> genreIds, List<Integer> authorIds) {
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

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public List<Integer> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<Integer> authorIds) {
        this.authorIds = authorIds;
    }
}
