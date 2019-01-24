package ru.otus.library.app.author.dto.request;

import javax.validation.constraints.NotBlank;

public class DtoCreateOrUpdateAuthorRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    public DtoCreateOrUpdateAuthorRequest() {
    }

    public DtoCreateOrUpdateAuthorRequest(@NotBlank String firstName, @NotBlank String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
