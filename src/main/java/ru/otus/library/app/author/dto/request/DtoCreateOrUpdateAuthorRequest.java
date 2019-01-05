package ru.otus.library.app.author.dto.request;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class DtoCreateOrUpdateAuthorRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private List<Integer> bookIds;

    public DtoCreateOrUpdateAuthorRequest() {
    }

    public DtoCreateOrUpdateAuthorRequest(@NotBlank String firstName, @NotBlank String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public DtoCreateOrUpdateAuthorRequest(@NotBlank String firstName, @NotBlank String lastName, List<Integer> bookIds) {
        this (firstName, lastName);
        this.bookIds = bookIds;
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

    public List<Integer> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Integer> bookIds) {
        this.bookIds = bookIds;
    }
}
