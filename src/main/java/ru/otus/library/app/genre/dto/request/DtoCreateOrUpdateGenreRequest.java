package ru.otus.library.app.genre.dto.request;

import javax.validation.constraints.NotBlank;

public class DtoCreateOrUpdateGenreRequest {

    @NotBlank
    private String name;

    public DtoCreateOrUpdateGenreRequest() {
    }

    public DtoCreateOrUpdateGenreRequest(@NotBlank String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
