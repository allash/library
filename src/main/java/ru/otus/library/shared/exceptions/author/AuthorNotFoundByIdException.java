package ru.otus.library.shared.exceptions.author;

import ru.otus.library.shared.exceptions.EntityNotFoundException;

public class AuthorNotFoundByIdException extends EntityNotFoundException {
    public AuthorNotFoundByIdException(Long id) { super(id.toString()); }
}
