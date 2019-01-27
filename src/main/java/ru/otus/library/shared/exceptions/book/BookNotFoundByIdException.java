package ru.otus.library.shared.exceptions.book;

import ru.otus.library.shared.exceptions.EntityNotFoundException;

public class BookNotFoundByIdException extends EntityNotFoundException {
    public BookNotFoundByIdException(Long id) { super(id.toString()); }
}
