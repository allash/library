package ru.otus.library.shared.exceptions.book;

import ru.otus.library.shared.exceptions.EntityNotFoundException;

public class BookNotFoundByIdException extends EntityNotFoundException {
    public BookNotFoundByIdException(Integer id) { super(id.toString()); }
}
