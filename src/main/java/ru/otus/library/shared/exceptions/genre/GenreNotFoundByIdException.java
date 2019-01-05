package ru.otus.library.shared.exceptions.genre;

import ru.otus.library.shared.exceptions.EntityNotFoundException;

public class GenreNotFoundByIdException extends EntityNotFoundException {
    public GenreNotFoundByIdException(Integer id) { super(id.toString()); }
}
